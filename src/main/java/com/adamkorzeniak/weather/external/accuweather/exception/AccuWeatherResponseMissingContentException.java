package com.adamkorzeniak.weather.external.accuweather.exception;

public class AccuWeatherResponseMissingContentException extends AccuWeatherException {

    public AccuWeatherResponseMissingContentException(String requestDetails, String field) {
        super(String.format("AccuWeather request %s didn't return %s", requestDetails, field));
    }
}
