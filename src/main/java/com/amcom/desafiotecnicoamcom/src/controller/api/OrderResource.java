package com.amcom.desafiotecnicoamcom.src.controller.api;

import com.amcom.desafiotecnicoamcom.src.domain.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @Operation(summary = "Endpoint para cancelar um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @DeleteMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Order> cancel(@PathVariable UUID orderId);

    @Operation(summary = "Endpoint para concluir um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido concluído com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PutMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Order> complete(@PathVariable UUID orderId);

    @Operation(summary = "Endpoint para buscar um pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca concluída com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Order> findById(@PathVariable UUID orderId);
}
