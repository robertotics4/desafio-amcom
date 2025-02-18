package com.amcom.desafiotecnicoamcom.src.infra.stream.consumer;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.CreateOrderService;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IConsumer;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer implements IConsumer {
    private final CreateOrderService createOrderService;
    private final JsonConverter jsonConverter;

    @KafkaListener(
            topics = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC,
            groupId = BrokerConstants.Consumer.GROUP_ID
    )
    public void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            log.info("[CONSUMER]: PEDIDO RECEBIDO - KEY={} | VALUE={}", record.key(), record.value());

            CreateOrderDTO createOrderDTO = jsonConverter.getObject(record.value(), CreateOrderDTO.class);
            Order order = this.createOrderService.execute(createOrderDTO);

            acknowledgment.acknowledge();

            log.info("[CONSUMER]: PEDIDO PROCESSADO - ID: {}", order.getId());
        } catch (Exception e) {
            log.error("[CONSUMER] ERROR: {}", e.getMessage());
        }
    }
}
