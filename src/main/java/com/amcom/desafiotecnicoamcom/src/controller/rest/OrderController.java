package com.amcom.desafiotecnicoamcom.src.controller.rest;

import com.amcom.desafiotecnicoamcom.src.controller.api.OrderResource;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import com.amcom.desafiotecnicoamcom.src.domain.service.ListOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController implements OrderResource {
    private final ListOrdersService listOrdersService;

    @Override
    public ResponseEntity<List<Order>> list() {
        List<Order> orders = this.listOrdersService.execute();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
