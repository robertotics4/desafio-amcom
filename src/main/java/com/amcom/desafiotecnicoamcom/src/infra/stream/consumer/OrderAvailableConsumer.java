package com.amcom.desafiotecnicoamcom.src.infra.stream.consumer;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderAvailableConsumer {
    @KafkaListener(
            topics = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC,
            groupId = BrokerConstants.Consumer.GROUP_ID
    )
    public void consumeOrder(ConsumerRecord<String, Order> record) {
        log.info("[KAFKA-CONSUMER] KEY={} | VALUE={}", record.key(), record.value());

    }
}
