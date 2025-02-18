package com.amcom.desafiotecnicoamcom.src.domain.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    AVAILABLE(0, "DISPONÍVEL"),
    PROCESSED(1, "PROCESSADO"),
    INTEGRATED(3, "PEDIDO INTEGRADO");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
