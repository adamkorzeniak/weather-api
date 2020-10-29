package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.api.model.response.CallRateLimits;
import com.adamkorzeniak.weather.external.accuweather.model.response.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class AccuweatherServiceImpl implements AccuweatherService {

    private final AccuWeatherRemoteService accuWeatherRemoteService;

    @Value("${server.timezone}")
    private String timezone;
    @Value("${service.accuweather.header.rate-limit}")
    private String rateLimitHeaderName;
    @Value("${service.accuweather.header.remaining-rate-limit}")
    private String remainingRateLimitHeaderName;


    @Autowired
    public AccuweatherServiceImpl(AccuWeatherRemoteService accuWeatherRemoteService) {
        this.accuWeatherRemoteService = accuWeatherRemoteService;
    }

    @Override
    public CallRateLimits getCallLimits() {
        //TODO: Zmień na wywołanie current conditions i dodaj komentarz czemu tak jest robione
        ResponseEntity<Location> response = accuWeatherRemoteService.getLocation();
        return getCallRateLimits(response);
    }

    private CallRateLimits getCallRateLimits(ResponseEntity<Location> response) {
        HttpHeaders headers = response.getHeaders();
        Optional<Long> rateLimit = headers.getOrEmpty(rateLimitHeaderName).stream().findFirst().map(x -> x + "s").map(Long::parseLong);
        Optional<Long> remainingRateLimit = headers.getOrEmpty(remainingRateLimitHeaderName).stream().findFirst().map(Long::parseLong);
        long date = headers.getDate();

        CallRateLimits rateLimits = new CallRateLimits();

        rateLimits.setDate(OffsetDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.of(timezone)));
        rateLimits.setRateLimit(rateLimit.orElse(null));
        rateLimits.setRemainingRateLimit(remainingRateLimit.orElse(null));
        return rateLimits;
    }
}
