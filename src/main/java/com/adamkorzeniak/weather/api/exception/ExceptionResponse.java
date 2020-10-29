package com.adamkorzeniak.weather.api.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
class ExceptionResponse {

    @JsonProperty("code")
    private final String code;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("message")
    private final String message;
}
