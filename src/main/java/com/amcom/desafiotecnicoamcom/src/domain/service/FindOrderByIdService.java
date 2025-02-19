package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.contract.IFindOrderByIdService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindOrderByIdService implements IFindOrderByIdService {
    private final OrderRepository orderRepository;

    @Override
    public Order execute(UUID orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
    }
}
