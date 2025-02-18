package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.contract.IIntegrateOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrateOrderService implements IIntegrateOrderService {
    private final JsonConverter jsonConverter;
    private final IProducer producer;

    @Override
    public Order execute(Order order) {
        String json = jsonConverter.getJsonOfObject(order);
        producer.produce(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC, order.getExternalId().toString(), json);

        log.info("[PRODUCER]: Integração pedido: ID {} | {}", order.getId(), BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC);

        return order;
    }
}
