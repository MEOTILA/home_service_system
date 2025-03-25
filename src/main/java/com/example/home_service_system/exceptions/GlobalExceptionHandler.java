package com.example.home_service_system.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomApiException.class})
    public ResponseEntity<Object> handleApplicationException(
            CustomApiException ex
    ) {
        return ResponseEntity
                .badRequest()
                .body(Map.of("errorMessage", ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        FieldError fe = ex.getBindingResult().getFieldErrors().get(0);
        var str = fe.getField() + " " + fe.getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(Map.of("errorMessage", str));
    }
}
