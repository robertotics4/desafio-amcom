package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.IReceiveOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.infra.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveOrderService implements IReceiveOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order execute(Order order) {
        Optional<Order> existingOrder = orderRepository.findById(order.getId());

        if (existingOrder.isEmpty()) {
            throw new NotFoundException("Pedido não encontrado");
        }

        if (existingOrder.get().getStatus().getCode() != OrderStatus.AVAILABLE.getCode()) {
            throw new BusinessException("O pedido não está mais disponível");
        }

        if (order.getProducts().isEmpty()) {
            throw new BusinessException("O pedido não possui produtos válidos");
        }

        validateProductsExist(order.getProducts());

        BigDecimal totalPrice = this.calculateTotalPrice(order.getProducts());
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.AVAILABLE);

        return orderRepository.save(order);
    }

    private void validateProductsExist(List<Product> products) {
        for (Product product : products) {
            Optional<Product> existingProduct = productRepository.findById(product.getId());
            if (existingProduct.isEmpty()) {
                throw new NotFoundException(String.format("O produto id %s não foi encontrado", product.getId()));
            }
        }
    }

    private BigDecimal calculateTotalPrice(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
