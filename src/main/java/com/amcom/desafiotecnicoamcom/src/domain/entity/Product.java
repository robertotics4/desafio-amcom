package com.amcom.desafiotecnicoamcom.src.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "O nome do produto não pode ser nulo")
    @Schema(description = "Nome do produto", example = "Produto A")
    private String name;

    @Column(name = "price", nullable = false)
    @Min(value = 0, message = "O preço deve ser maior ou igual a 0")
    @Schema(description = "Preço do produto", example = "49.99")
    private BigDecimal price;
}
