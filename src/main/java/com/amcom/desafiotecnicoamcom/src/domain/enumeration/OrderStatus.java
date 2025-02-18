package com.amcom.desafiotecnicoamcom.src.domain.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PROCESSED(1, "PROCESSADO");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
