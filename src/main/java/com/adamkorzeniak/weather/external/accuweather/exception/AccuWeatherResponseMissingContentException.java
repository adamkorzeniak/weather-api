package com.adamkorzeniak.weather.external.accuweather.exception;

public class AccuWeatherResponseMissingContentException extends AccuWeatherException {

    public AccuWeatherResponseMissingContentException(String requestDetails) {
        super(String.format("AccuWeather request %s didn't return any elements", requestDetails));
    }
}
