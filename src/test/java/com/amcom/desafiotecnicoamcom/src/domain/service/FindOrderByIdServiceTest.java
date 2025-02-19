package com.amcom.desafiotecnicoamcom.src.domain.service;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
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
public class FindOrderByIdServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private FindOrderByIdService sut;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order();
        order.setId(orderId);
    }

    @Test
    void shouldReturnOrderWhenFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order result = sut.execute(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderNotFound() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sut.execute(orderId));
        verify(orderRepository, times(1)).findById(orderId);
    }
}
