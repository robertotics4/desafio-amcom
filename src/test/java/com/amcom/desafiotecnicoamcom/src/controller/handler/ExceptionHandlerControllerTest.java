package com.amcom.desafiotecnicoamcom.src.controller.handler;

import com.amcom.desafiotecnicoamcom.src.domain.dto.ErrorDTO;
import com.amcom.desafiotecnicoamcom.src.domain.exception.BadRequestException;
import com.amcom.desafiotecnicoamcom.src.domain.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.UnexpectedTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerControllerTest {
    @InjectMocks
    private ExceptionHandlerController sut;

    @Mock
    private HttpServletRequest request;

    private BadRequestException badRequestException;
    private NotFoundException notFoundException;

    @BeforeEach
    void setUp() {
        badRequestException = new BadRequestException("Bad Request");
        notFoundException = new NotFoundException("Not Found");
    }

    @Test
    void shouldHandleBadRequestException() {
        when(request.getServletPath()).thenReturn("/test");

        ResponseEntity<ErrorDTO> response = sut.handleBadRequest(badRequestException, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().getMessage());
        assertEquals("/test", response.getBody().getPath());
    }

    @Test
    void shouldHandleNotFoundException() {
        when(request.getServletPath()).thenReturn("/test");

        ResponseEntity<ErrorDTO> response = sut.handleRecursoNaoEncontradoException(notFoundException, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not Found", response.getBody().getMessage());
        assertEquals("/test", response.getBody().getPath());
    }

    @Test
    void shouldHandleHttpRequestMethodNotSupportedException() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("POST");

        ResponseEntity<ErrorDTO> response = sut.handleHttpRequestMethodNotSupportedException(exception, request);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        assertEquals("Method Not Allowed", response.getBody().getError());
    }

    @Test
    void shouldHandleUnexpectedTypeException() {
        UnexpectedTypeException exception = new UnexpectedTypeException("Unexpected type", null);

        ResponseEntity<ErrorDTO> response = sut.handleUnexpectedTypeException(exception, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Unexpected type", response.getBody().getMessage());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "field", "message");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ErrorDTO> response = sut.tratarErroArgumentoInvalido(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Dados invalidos.", response.getBody().getMessage());
    }

    @Test
    void shouldHandleConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn("field");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("invalid");

        Set<ConstraintViolation<?>> violations = Set.of(violation);
        ConstraintViolationException exception = new ConstraintViolationException(violations);

        ResponseEntity<ErrorDTO> response = sut.handleConstraintViolationException(exception, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Parametros invalidos.", response.getBody().getMessage());
    }


    @Test
    void shouldHandleException() {
        Exception exception = new Exception("Internal Server Error");

        ResponseEntity<ErrorDTO> response = sut.handleException(exception, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error", response.getBody().getMessage());
    }
}
