package com.amcom.desafiotecnicoamcom.src.infra.stream.producer;

import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer implements IProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        kafkaTemplate.send(record);
    }
}
