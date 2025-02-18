package com.amcom.desafiotecnicoamcom.src.domain.dto;

import java.util.UUID;

public record ProductOrderDTO(
        UUID id,
        int quantity
) {}
