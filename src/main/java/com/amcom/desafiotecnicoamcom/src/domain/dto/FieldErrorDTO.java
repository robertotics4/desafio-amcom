package com.amcom.desafiotecnicoamcom.src.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record FieldErrorDTO(String fieldName, String reason) implements Serializable, Comparable<FieldErrorDTO> {

    @Override
    public int compareTo(@NotNull FieldErrorDTO o) {
        return fieldName.compareTo(o.fieldName);
    }
}
