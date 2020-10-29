package com.adamkorzeniak.weather.api.controller;

import com.adamkorzeniak.weather.api.model.response.CallRateLimits;
import com.adamkorzeniak.weather.external.accuweather.service.AccuweatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v0/rates")
public class RatesController {

    private final AccuweatherService accuweatherService;

    @Autowired
    public RatesController(AccuweatherService accuweatherService) {
        this.accuweatherService = accuweatherService;
    }

    @GetMapping("")
    //TODO: Dodaj AOP Logger i MDC z correlationId
    public ResponseEntity<CallRateLimits> getCallRateLimits() {
        CallRateLimits rateLimits = accuweatherService.getCallLimits();
        return new ResponseEntity<>(rateLimits, HttpStatus.OK);
    }
}
