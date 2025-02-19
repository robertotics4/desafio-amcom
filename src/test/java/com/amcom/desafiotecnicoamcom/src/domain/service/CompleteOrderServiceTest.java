package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.config.jackson.JsonConverter;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BusinessException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.contract.IProducer;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import com.amcom.desafiotecnicoamcom.src.support.constants.BrokerConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompleteOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private IProducer producer;

    @InjectMocks
    private CompleteOrderService sut;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order();
        order.setId(orderId);
        order.setStatus(OrderStatus.PENDING);
        order.setExternalId(UUID.randomUUID());
    }

    @Test
    void shouldCompleteOrderSuccessfully() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(jsonConverter.getJsonOfObject(order)).thenReturn("{}");
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = sut.execute(orderId);

        assertEquals(OrderStatus.COMPLETED, result.getStatus());
        verify(producer).produce(eq(BrokerConstants.Topics.EXTERNAL_PROCESSED_ORDER_TOPIC), eq(order.getExternalId().toString()), anyString());
        verify(orderRepository).save(order);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sut.execute(orderId));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void shouldThrowBusinessExceptionWhenOrderIsNotPending() {
        order.setStatus(OrderStatus.COMPLETED);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(BusinessException.class, () -> sut.execute(orderId));
        verify(producer, never()).produce(any(), any(), any());
        verify(orderRepository, never()).save(any());
    }
}
