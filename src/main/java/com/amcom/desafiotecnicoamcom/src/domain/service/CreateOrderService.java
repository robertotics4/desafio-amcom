package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.contract.ICreateOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.dto.ProductOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.entity.OrderProduct;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService implements ICreateOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final IProducer producer;
    private final JsonConverter jsonConverter;

    @Override
    @Transactional
    @Retryable(value = BusinessException.class, backoff = @Backoff(delay = 2000))
    public Order execute(CreateOrderDTO dto) {
        log.info("[SERVICE]: PROCESSAMENTO DO PEDIDO - EXTERNAL ID {}", dto.externalId());

        if (orderRepository.existsByExternalId(dto.externalId())) {
            this.sendToDlqTopic(dto);
            throw new BusinessException("Pedido já existente ID " + dto.externalId());
        }

        List<OrderProduct> orderProducts = mapOrderProducts(dto);
        BigDecimal totalPrice = calculateTotalPrice(orderProducts);
        Order order = Order.builder()
                .externalId(dto.externalId())
                .status(OrderStatus.PROCESSED)
                .totalPrice(totalPrice)
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(op -> op.setOrder(order));

        Order createdOrder = orderRepository.save(order);
        String orderJson = jsonConverter.getJsonOfObject(createdOrder);

        log.info("[PRODUCER]: ENVIANDO {} para {}", orderJson, BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC);
        this.producer.produce(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC, createdOrder.getExternalId().toString(), orderJson);

        return createdOrder;
    }

    private List<OrderProduct> mapOrderProducts(CreateOrderDTO dto) {
        List<ProductOrderDTO> orderProducts = dto.products();

        if (orderProducts.isEmpty()) {
            this.sendToDlqTopic(dto);
            throw new BusinessException("O pedido não possui produtos");
        }

        List<UUID> productIds = orderProducts.stream()
                .map(ProductOrderDTO::id)
                .collect(Collectors.toList());

        List<Product> existingProducts = productRepository.findAllById(productIds);

        if (existingProducts.size() != productIds.size()) {
            this.sendToDlqTopic(dto);
            throw new BusinessException("O pedido contém produtos inválidos");
        }

        return orderProducts.stream()
                .map(productOrder -> {
                    Product product = existingProducts.stream()
                            .filter(p -> p.getId().equals(productOrder.id()))
                            .findFirst()
                            .orElseThrow(() -> new BusinessException("Produto não encontrado: " + productOrder.id()));

                    return OrderProduct.builder()
                            .product(product)
                            .quantity(productOrder.quantity())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(op -> op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void sendToDlqTopic(CreateOrderDTO dto) {
        String dlqTopic = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq";
        log.info("[PRODUCER]: Enviando {} para DLQ {}", dto.externalId(), dlqTopic);
        this.producer.produce(dlqTopic, dto.externalId().toString(), jsonConverter.getJsonOfObject(dto));
    }
}
