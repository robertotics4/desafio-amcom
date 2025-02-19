package com.amcom.desafiotecnicoamcom.src.infra.stream.consumer;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateOrderDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.CreateOrderService;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderConsumerTest {
    @Mock
    private CreateOrderService createOrderService;

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private OrderConsumer sut;

    private String invalidJson;
    private CreateOrderDTO createOrderDTO;
    private ConsumerRecord<String, String> consumerRecord;

    @BeforeEach
    void setUp() {
        String validJson = "{\"externalId\":\"valid-external-id\", \"products\":[{\"id\":\"product-id\", \"quantity\":2}]}";
        invalidJson = "invalid-json";
        createOrderDTO = new CreateOrderDTO(null, null);
        consumerRecord = new ConsumerRecord<>(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC, 0, 0L, "key", validJson);

        lenient().when(jsonConverter.getObject(validJson, CreateOrderDTO.class)).thenReturn(createOrderDTO);
        lenient().when(jsonConverter.getObject(invalidJson, CreateOrderDTO.class)).thenThrow(new RuntimeException("Invalid JSON"));
    }

    @Test
    void shouldProcessOrderSuccessfully() {
        when(createOrderService.execute(createOrderDTO)).thenReturn(new Order());

        sut.consume(consumerRecord, acknowledgment);

        verify(createOrderService, times(1)).execute(createOrderDTO);
        verify(acknowledgment, times(1)).acknowledge();
    }

    @Test
    void shouldHandleExceptionWhenProcessingOrder() {
        when(createOrderService.execute(createOrderDTO)).thenThrow(new RuntimeException("Service error"));

        sut.consume(consumerRecord, acknowledgment);

        verify(acknowledgment, times(1)).acknowledge();
    }

    @Test
    void shouldHandleInvalidJson() {
        ConsumerRecord<String, String> invalidConsumerRecord = new ConsumerRecord<>(BrokerConstants.Topics.EXTERNAL_AVAILABLE_ORDER_TOPIC, 0, 0L, "key", invalidJson);
        sut.consume(invalidConsumerRecord, acknowledgment);

        verify(acknowledgment, times(1)).acknowledge();
    }
}
