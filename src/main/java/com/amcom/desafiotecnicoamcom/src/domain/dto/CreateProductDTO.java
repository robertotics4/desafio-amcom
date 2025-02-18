package com.amcom.desafiotecnicoamcom.src.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CreateProductDTO(
        @NotBlank(message = "O nome do produto não pode ser vazio")
        String name,

        @NotNull(message = "O preço não pode ser nulo")
        @Positive(message = "O preço deve ser positivo")
        BigDecimal price
) {}
