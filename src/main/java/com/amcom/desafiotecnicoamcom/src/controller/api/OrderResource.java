package com.amcom.desafiotecnicoamcom.src.controller.api;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Pedidos", description = "Endpoints destinados a lidar com gerenciamento de pedidos.")
@RestController
@RequestMapping("/v1/orders")
public interface OrderResource {


    @Operation(summary = "Endpoint para listar pedidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Order>> list();
}
