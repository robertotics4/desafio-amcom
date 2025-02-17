package com.amcom.desafiotecnicoamcom.src.infra.stream.consumer;

import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import org.springframework.kafka.annotation.KafkaListener;

public class OrderAvailableConsumer {
    @KafkaListener(topics = BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC, groupId = BrokerConstants.Consumer.GROUP_ID)
    public void listen(String message) {
        System.out.println("Mensagem recebida: " + message);
    }
}
