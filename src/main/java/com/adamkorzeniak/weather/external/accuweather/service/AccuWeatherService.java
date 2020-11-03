package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.external.accuweather.model.response.AccuForecast;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuLocationDetails;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuWeather;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccuWeatherService {

    ResponseEntity<List<AccuLocationDetails>> getLocation(String postalCode);

    ResponseEntity<List<AccuWeather>> getCurrentConditions(String locationKey);

    ResponseEntity<AccuForecast> getForecasts(String locationKey);
}
