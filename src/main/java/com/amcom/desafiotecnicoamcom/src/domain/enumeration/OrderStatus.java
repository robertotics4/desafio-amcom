package com.amcom.desafiotecnicoamcom.src.domain.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    RECEIVED(0, "RECEBIDO"),
    PROCESSED(1, "PROCESSADO"),
    ERROR(99, "ERRO");

    private final int code;
    private final String description;

    OrderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
