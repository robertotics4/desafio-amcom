package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.IReceiveOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiveOrderService implements IReceiveOrderService {
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order execute(Order order) {
        Order currentOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (currentOrder.getStatus() != OrderStatus.AVAILABLE) {
            throw new BusinessException("O pedido não está mais disponível");
        }

        log.info("Executando algum processamento no pedido ...");

        order.setStatus(OrderStatus.PROCESSED);
        return orderRepository.save(order);
    }
}
