package com.amcom.desafiotecnicoamcom.src.controller.api;

import com.amcom.desafiotecnicoamcom.src.domain.dto.CreateProductDTO;
import com.amcom.desafiotecnicoamcom.src.domain.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Endpoints destinados a lidar com gerenciamento de produtos.")
@RestController
@RequestMapping("/v1/products")
public interface ProductResource {

    @Operation(summary = "Endpoint para listar produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Product>> list();

    @Operation(summary = "Endpoint para criar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição com formato inválido.", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Product> create(@RequestBody CreateProductDTO dto);
}
