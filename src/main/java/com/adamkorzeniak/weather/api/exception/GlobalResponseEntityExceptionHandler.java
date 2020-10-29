package com.adamkorzeniak.weather.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_CODE = "ERR000";
    private static final String INTERNAL_SERVER_ERROR_TITLE = "Unexpected Internal Server Error";

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Object> defaultException(Exception exc, WebRequest request) {
        ExceptionResponse bodyOfResponse = new ExceptionResponse(
                INTERNAL_SERVER_ERROR_CODE,
                INTERNAL_SERVER_ERROR_TITLE,
                exc.getMessage());
        return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}