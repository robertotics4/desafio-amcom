package com.amcom.desafiotecnicoamcom.src.controller.rest;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.CancelOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.service.CompleteOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.service.FindOrderByIdService;
import com.amcom.desafiotecnicoamcom.src.domain.service.ListOrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @Mock
    private ListOrdersService listOrdersService;

    @Mock
    private CancelOrderService cancelOrderService;

    @Mock
    private CompleteOrderService completeOrderService;

    @Mock
    private FindOrderByIdService findOrderByIdService;

    @InjectMocks
    private OrderController sut;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order();
        order.setId(orderId);
    }

    @Test
    void shouldListOrders() {
        when(listOrdersService.execute()).thenReturn(List.of(order));

        ResponseEntity<List<Order>> response = sut.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(listOrdersService, times(1)).execute();
    }

    @Test
    void shouldCancelOrder() {
        when(cancelOrderService.execute(orderId)).thenReturn(order);

        ResponseEntity<Order> response = sut.cancel(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());
        verify(cancelOrderService, times(1)).execute(orderId);
    }

    @Test
    void shouldCompleteOrder() {
        when(completeOrderService.execute(orderId)).thenReturn(order);

        ResponseEntity<Order> response = sut.complete(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());
        verify(completeOrderService, times(1)).execute(orderId);
    }

    @Test
    void shouldFindOrderById() {
        when(findOrderByIdService.execute(orderId)).thenReturn(order);

        ResponseEntity<Order> response = sut.findById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getId());
        verify(findOrderByIdService, times(1)).execute(orderId);
    }
}
