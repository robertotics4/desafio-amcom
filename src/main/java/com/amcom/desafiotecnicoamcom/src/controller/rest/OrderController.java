package com.amcom.desafiotecnicoamcom.src.controller.rest;

import com.amcom.desafiotecnicoamcom.src.controller.api.OrderResource;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.CancelOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.service.CompleteOrderService;
import com.amcom.desafiotecnicoamcom.src.domain.service.FindOrderByIdService;
import com.amcom.desafiotecnicoamcom.src.domain.service.ListOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController implements OrderResource {
    private final ListOrdersService listOrdersService;
    private final CancelOrderService cancelOrderService;
    private final CompleteOrderService completeOrderService;
    private final FindOrderByIdService findOrderByIdService;

    @Override
    public ResponseEntity<List<Order>> list() {
        List<Order> orders = this.listOrdersService.execute();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> cancel(UUID orderId) {
        Order order = this.cancelOrderService.execute(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> complete(UUID orderId) {
        Order order = this.completeOrderService.execute(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Order> findById(UUID orderId) {
        Order order = this.findOrderByIdService.execute(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
