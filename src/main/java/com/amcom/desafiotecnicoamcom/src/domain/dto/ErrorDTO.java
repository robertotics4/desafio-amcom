package com.amcom.desafiotecnicoamcom.src.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;

    private int status;

    private String error;

    private String message;

    private String path;

    private Set<FieldErrorDTO> violations;

    public void addViolation(String fieldName, String message) {
        violations.add(new FieldErrorDTO(fieldName, message));
    }
}
