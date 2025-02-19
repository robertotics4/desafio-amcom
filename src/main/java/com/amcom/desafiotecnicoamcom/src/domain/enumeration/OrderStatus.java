package com.amcom.desafiotecnicoamcom.src.domain.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING(0, "PENDENTE"),
    COMPLETED(1, "CONCLUÍDO"),
    CANCELED(3, "CANCELADO");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
