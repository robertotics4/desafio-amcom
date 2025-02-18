package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.ICreateOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.dto.ProductOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.entity.OrderProduct;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrderService implements ICreateOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order execute(CreateOrderDTO dto) {
        if (orderRepository.existsByExternalId(dto.externalId())) {
            throw new BusinessException("Já existe um pedido para o id " + dto.externalId());
        }

        List<OrderProduct> orderProducts = mapOrderProducts(dto.products());

        BigDecimal totalPrice = calculateTotalPrice(orderProducts);

        Order order = Order.builder()
                .externalId(dto.externalId())
                .status(OrderStatus.AVAILABLE)
                .totalPrice(totalPrice)
                .orderProducts(orderProducts)
                .build();

        orderProducts.forEach(op -> op.setOrder(order));

        return orderRepository.save(order);
    }

    private List<OrderProduct> mapOrderProducts(List<ProductOrderDTO> orderProducts) {
        if (orderProducts.isEmpty()) {
            throw new BusinessException("O pedido não possui produtos");
        }

        List<UUID> productIds = orderProducts.stream()
                .map(ProductOrderDTO::id)
                .collect(Collectors.toList());

        List<Product> existingProducts = productRepository.findAllById(productIds);

        if (existingProducts.size() != productIds.size()) {
            throw new BusinessException("O pedido contém produtos inválidos");
        }

        return orderProducts.stream()
                .map(dto -> {
                    Product product = existingProducts.stream()
                            .filter(p -> p.getId().equals(dto.id()))
                            .findFirst()
                            .orElseThrow(() -> new BusinessException("Produto não encontrado: " + dto.id()));

                    return OrderProduct.builder()
                            .product(product)
                            .quantity(dto.quantity())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(op -> op.getProduct().getPrice().multiply(BigDecimal.valueOf(op.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
