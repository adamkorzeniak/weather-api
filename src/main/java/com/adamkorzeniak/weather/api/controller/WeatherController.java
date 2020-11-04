package com.adamkorzeniak.weather.api.controller;

import com.adamkorzeniak.weather.api.model.DataWithContext;
import com.adamkorzeniak.weather.api.model.response.DailyForecast;
import com.adamkorzeniak.weather.api.model.response.Weather;
import com.adamkorzeniak.weather.api.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v0/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/current")
    public ResponseEntity<Weather> getCurrentWeather(@RequestParam("postalCode") String postalCode) {
        DataWithContext<Weather> weather = weatherService.getCurrentConditions(postalCode);
        return buildResponse(weather);
    }

    @GetMapping("/forecast")
    public ResponseEntity<List<DailyForecast>> getForecast(@RequestParam("postalCode") String postalCode) {
        DataWithContext<List<DailyForecast>> forecast = weatherService.getForecast(postalCode);
        return buildResponse(forecast);
    }

    private <T> ResponseEntity<T> buildResponse(DataWithContext<T> result) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("RateLimit", result.getRateLimit());
        responseHeaders.set("RateLimitRemaining", result.getRemainingRateLimit());

        return ResponseEntity.ok().headers(responseHeaders).body(result.getData());
    }
}
