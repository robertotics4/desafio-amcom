package com.amcom.desafiotecnicoamcom.src.controller.api;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/orders")
public interface OrderResource {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Order>> list();
}
