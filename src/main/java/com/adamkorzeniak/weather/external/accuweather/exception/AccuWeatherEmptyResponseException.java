package com.adamkorzeniak.weather.external.accuweather.exception;

public class AccuWeatherEmptyResponseException extends AccuWeatherException {

    public AccuWeatherEmptyResponseException(String requestDetails) {
        super(String.format("AccuWeather request %s returned empty response", requestDetails));
    }
}
