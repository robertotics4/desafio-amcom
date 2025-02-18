package com.amcom.desafiotecnicoamcom.src.config.kafka;

import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BrokerConfig {
    private static final int PARTITIONS = 1;
    private static final short REPLICAS = 1;

    @Bean
    public NewTopic availableOrderTopic() {
        return TopicBuilder.name(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC)
                .partitions(PARTITIONS)
                .replicas(REPLICAS)
                .build();
    }

    @Bean
    public NewTopic processedOrderTopic() {
        return TopicBuilder.name(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC)
                .partitions(PARTITIONS)
                .replicas(REPLICAS)
                .build();
    }

    @Bean
    public NewTopic availableOrderDeadLetterTopic() {
        return TopicBuilder.name(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC + ".dlq")
                .partitions(PARTITIONS)
                .replicas(REPLICAS)
                .build();
    }

//    @Bean
//    public NewTopic processedOrderDeadLetterTopic() {
//        return TopicBuilder.name(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC + ".dlq")
//                .partitions(PARTITIONS)
//                .replicas(REPLICAS)
//                .build();
//    }
}
