package com.amcom.desafiotecnicoamcom.src.infra.contract;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface IConsumer {
    void consume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment);
}
