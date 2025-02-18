package com.amcom.desafiotecnicoamcom.src.controller.handler;

import com.amcom.desafiotecnicoamcom.src.domain.dto.ErrorDTO;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BadRequestException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = {BadRequestException.class, MissingRequestValueException.class})
    public ResponseEntity<ErrorDTO> handleBadRequest(Exception e, HttpServletRequest request) {
        log.error("Erro de validacao de regra de negocio.", e);
        ErrorDTO err = generateError(request, HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(err);
    }


    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorDTO> handleRecursoNaoEncontradoException(NotFoundException e, HttpServletRequest request) {
        log.error("Recurso nao encontrado.", e);
        ErrorDTO err = generateError(request, HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("Metodo/verbo HTTP nao suportado.", e);
        ErrorDTO err = generateError(request, HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorDTO> handleUnexpectedTypeException(UnexpectedTypeException e, HttpServletRequest request) {
        log.error("Tipo de dado inesperado.", e);
        ErrorDTO err = generateError(request, HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> tratarErroArgumentoInvalido(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("Dados invalidos.", e);
        ErrorDTO err = generateError(request, HttpStatus.BAD_REQUEST, "Dados invalidos.");

        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
        fieldErrorList.forEach(f -> err.addViolation(f.getField(), f.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("Parametros invalidos.", e);
        ErrorDTO err = generateError(request, HttpStatus.BAD_REQUEST, "Parametros invalidos.");

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        violations.forEach(f -> err.addViolation(f.getPropertyPath().toString(), f.getMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDTO> handleException(Exception e, HttpServletRequest request) {
        log.error("Erro de infraestrutura do servico.", e);
        ErrorDTO err = generateError(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(err);
    }

    private ErrorDTO generateError(HttpServletRequest request, HttpStatus httpStatus, String message) {
        return ErrorDTO.builder()
                .timeStamp(LocalDateTime.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(request.getServletPath())
                .violations(new TreeSet<>())
                .build();
    }
}
