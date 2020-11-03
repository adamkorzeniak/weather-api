package com.adamkorzeniak.weather.api.exception;

import com.adamkorzeniak.weather.external.accuweather.exception.AccuWeatherException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String API_KEY_PARAM_REGEX = "apikey=[\\w]+";
    private static final String API_KEY_PARAM_PLACEHOLDER = "apikey=XXX";

    private static final String INTERNAL_SERVER_ERROR_CODE = "ERR000";
    private static final String INTERNAL_SERVER_ERROR_TITLE = "Unexpected Internal Server Error";

    private static final String DEPENDENT_SERVER_ERROR_CODE = "ERR001";
    private static final String DEPENDENT_SERVER_ERROR_TITLE = "Unexpected Dependent Server Error";

    @ExceptionHandler({HttpStatusCodeException.class})
    protected ResponseEntity<Object> dependentServerError(HttpStatusCodeException exc, WebRequest request) {
        String message = exc.getMessage() == null
                ? String.format("Dependent Server returned %s with no content", exc.getStatusCode())
                : String.format("Dependent Server returned %s", exc.getMessage().replaceAll(API_KEY_PARAM_REGEX, API_KEY_PARAM_PLACEHOLDER));

        ExceptionResponse bodyOfResponse = new ExceptionResponse(
                DEPENDENT_SERVER_ERROR_CODE,
                DEPENDENT_SERVER_ERROR_TITLE,
                message);
        return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({AccuWeatherException.class})
    protected ResponseEntity<Object> accuWeatherException(AccuWeatherException exc, WebRequest request) {
        ExceptionResponse bodyOfResponse = new ExceptionResponse(
                DEPENDENT_SERVER_ERROR_CODE,
                DEPENDENT_SERVER_ERROR_TITLE,
                exc.getMessage());
        return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {Throwable.class})
    protected ResponseEntity<Object> defaultException(Exception exc, WebRequest request) {
        ExceptionResponse bodyOfResponse = new ExceptionResponse(
                INTERNAL_SERVER_ERROR_CODE,
                INTERNAL_SERVER_ERROR_TITLE,
                exc.getMessage());
        return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}