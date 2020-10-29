package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.external.accuweather.model.response.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccuWeatherRemoteServiceImpl implements AccuWeatherRemoteService {

    @Value("${service.accuweather.url}")
    private String accuweatherUrl;
    @Value("${service.accuweather.apiKey}")
    private String apiKey;
    @Value("${service.accuweather.path.locations}")
    private String getLocationsPath;

    @Override
    public ResponseEntity<Location> getLocation() {
        String url = accuweatherUrl + getLocationsPath + "/14?apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, Location.class);
    }
}
