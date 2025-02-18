package com.amcom.desafiotecnicoamcom.src.domain.dto;

import java.util.List;
import java.util.UUID;

public record CreateOrderDTO(
        UUID externalId,
        List<ProductOrderDTO> products
) {}
