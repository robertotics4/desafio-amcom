package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.ICancelOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderService implements ICancelOrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order execute(UUID orderId) {
        Optional<Order> existingOrder = this.orderRepository.findById(orderId);

        if (existingOrder.isEmpty()) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Order order = existingOrder.get();
        order.setStatus(OrderStatus.CANCELED);

        return this.orderRepository.save(order);
    }
}
