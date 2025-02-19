package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.contract.ICompleteOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompleteOrderService implements ICompleteOrderService {
    private final OrderRepository orderRepository;
    private final JsonConverter jsonConverter;
    private final IProducer producer;

    @Override
    public Order execute(UUID orderId) {
        Optional<Order> existingOrder = this.orderRepository.findById(orderId);

        if (existingOrder.isEmpty()) {
            throw new NotFoundException("Pedido não encontrado");
        }

        Order order = existingOrder.get();

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BusinessException("O pedido precisa estar 'PENDENTE' para ser concluído");
        }

        String json = jsonConverter.getJsonOfObject(order);
        producer.produce(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC, order.getExternalId().toString(), json);
        order.setStatus(OrderStatus.COMPLETED);

        return orderRepository.save(order);
    }
}
