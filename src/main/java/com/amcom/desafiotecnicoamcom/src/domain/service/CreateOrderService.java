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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    public Order execute(CreateOrderDTO dto) {
        log.info("[SERVICE]: PROCESSANDO PEDIDO - ID {}", dto.externalId());

        if (orderRepository.existsByExternalId(dto.externalId())) {
            sendToDlq(dto, "Pedido já existente");
        }

        List<OrderProduct> orderProducts = mapOrderProducts(dto);

        return buildOrder(dto.externalId(), orderProducts);
    }

    private List<OrderProduct> mapOrderProducts(CreateOrderDTO dto) {
        if (dto.products().isEmpty()) {
            sendToDlq(dto, "Pedido sem produtos");
        }

        List<UUID> productIds = dto.products().stream().map(ProductOrderDTO::id).toList();
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            sendToDlq(dto, "Pedido contém produtos inválidos");
        }

        return dto.products().stream().map(productOrder -> {
            Product product = products.stream()
                    .filter(p -> p.getId().equals(productOrder.id()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException("Produto não encontrado: " + productOrder.id()));

            return OrderProduct.builder()
                    .product(product)
                    .quantity(productOrder.quantity())
                    .build();
        }).toList();
    }

    private Order buildOrder(UUID externalId, List<OrderProduct> orderProducts) {
        BigDecimal totalPrice = orderProducts.stream()
                .map(op -> op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .externalId(externalId)
                .status(OrderStatus.PENDING)
                .totalPrice(totalPrice)
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(op -> op.setOrder(order));

        orderRepository.save(order);

        return order;
    }

    private void sendToDlq(CreateOrderDTO dto, String message) {
        String dlqTopic = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq";
        log.info("[PRODUCER]: ENVIANDO {} PARA DLQ {} - MOTIVO: {}", dto.externalId(), dlqTopic, message);
        producer.produce(dlqTopic, dto.externalId().toString(), jsonConverter.getJsonOfObject(dto));
        throw new BusinessException(message + " ID " + dto.externalId());
    }
}
