package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.enumeration.OrderStatus;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import com.amcom.desafiotecnicoamcom.src.infra.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancelOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CancelOrderService sut;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = Order.builder()
                .id(orderId)
                .status(OrderStatus.PENDING)
                .build();
    }

    @Test
    void shouldCancelExistingOrder() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order canceledOrder = sut.execute(orderId);

        assertNotNull(canceledOrder);
        assertEquals(OrderStatus.CANCELED, canceledOrder.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderDoesNotExist() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> sut.execute(orderId));
        assertEquals("Pedido não encontrado", exception.getMessage());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, never()).save(any(Order.class));
    }
}
