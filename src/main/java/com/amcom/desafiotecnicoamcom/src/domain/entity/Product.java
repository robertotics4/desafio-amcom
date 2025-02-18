package com.amcom.desafiotecnicoamcom.src.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um produto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @Schema(description = "Identificador único do produto", example = "123e4567-e89b-12d3-a456-426614174002")
    private UUID id;

    @Column(name = "name", nullable = false)
    @Schema(description = "Nome do produto", example = "Produto A")
    private String name;

    @Column(name = "price", nullable = false)
    @Schema(description = "Preço do produto", example = "49.99")
    private BigDecimal price;
}
