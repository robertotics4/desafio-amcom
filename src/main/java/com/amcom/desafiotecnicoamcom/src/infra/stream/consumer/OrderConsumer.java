package com.amcom.desafiotecnicoamcom.src.infra.stream.consumer;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.CreateOrderService;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final CreateOrderService createOrderService;
    private final JsonConverter jsonConverter;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(
            topics = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC,
            groupId = BrokerConstants.Consumer.GROUP_ID
    )
    public void consumeOrder(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        int retries = 0;
        boolean processed = false;

        int MAX_RETRIES = 3;
        while (retries < MAX_RETRIES && !processed) {
            try {
                log.info("[KAFKA-CONSUMER] KEY={} | VALUE={}", record.key(), record.value());

                CreateOrderDTO createOrderDTO = jsonConverter.getObject(record.value().toString(), CreateOrderDTO.class);
                Order order = this.createOrderService.execute(createOrderDTO);

                acknowledgment.acknowledge();

                log.info("[KAFKA-CONSUMER] Order processed id: {}", order.getId());
                processed = true;
            } catch (Exception e) {
                retries++;
                log.error("[KAFKA-CONSUMER] ERROR: {}", e.getMessage());

                if (retries >= MAX_RETRIES) {
                    log.error("[KAFKA-CONSUMER] Max retries reached. Sending message to DLQ.");

                    String DLQ_TOPIC = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq";
                    kafkaTemplate.send(DLQ_TOPIC, record.key(), record.value());

                    acknowledgment.nack(Duration.ofSeconds(2));
                }
            }
        }
    }
}
