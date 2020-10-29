package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.external.accuweather.model.response.Location;
import org.springframework.http.ResponseEntity;

public interface AccuWeatherRemoteService {

    ResponseEntity<Location> getLocation();
}
