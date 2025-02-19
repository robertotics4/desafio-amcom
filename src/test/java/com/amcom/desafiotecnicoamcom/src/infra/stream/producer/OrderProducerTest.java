package com.amcom.desafiotecnicoamcom.src.infra.stream.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProducerTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private OrderProducer sut;

    private String topic;
    private String key;
    private String message;

    @BeforeEach
    void setUp() {
        topic = "test-topic";
        key = "test-key";
        message = "test-message";
    }

    @Test
    void shouldSendMessageToKafka() {
        sut.produce(topic, key, message);

        ProducerRecord<String, String> expectedRecord = new ProducerRecord<>(topic, key, message);
        verify(kafkaTemplate, times(1)).send(expectedRecord);
    }

    @Test
    void shouldSendMessageToKafkaWhenKeyIsNull() {
        sut.produce(topic, null, message);

        ProducerRecord<String, String> expectedRecord = new ProducerRecord<>(topic, null, message);
        verify(kafkaTemplate, times(1)).send(expectedRecord);
    }

    @Test
    void shouldSendMessageToKafkaWhenMessageIsEmpty() {
        sut.produce(topic, key, "");

        ProducerRecord<String, String> expectedRecord = new ProducerRecord<>(topic, key, "");
        verify(kafkaTemplate, times(1)).send(expectedRecord);
    }
}
