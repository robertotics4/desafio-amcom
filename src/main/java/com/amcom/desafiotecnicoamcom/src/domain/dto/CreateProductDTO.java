package com.amcom.desafiotecnicoamcom.src.domain.dto;

import java.math.BigDecimal;

public record CreateProductDTO(
        String name,
        BigDecimal price
) {}
