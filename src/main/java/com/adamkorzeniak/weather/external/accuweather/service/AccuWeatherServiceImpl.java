package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.external.accuweather.model.response.AccuForecast;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuLocationDetails;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccuWeatherServiceImpl implements AccuWeatherService {

    private static final String API_KEY_PARAM = "apikey";
    private static final String QUERY_PARAM = "q";
    private static final String LANGUAGE_PARAM = "language";
    private static final String METRIC_PARAM = "metric";
    private static final String LOCATION_KEY_PARAM = "locationKey";

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.accuweather.apiKey}")
    private String apiKey;

    @Value("${service.accuweather.configuration.language}")
    private String language;
    @Value("${service.accuweather.configuration.metric}")
    private String metric;

    @Value("${service.accuweather.url}${service.accuweather.path.postalCodeSearch}")
    private String postalCodeSearchUrl;
    @Value("${service.accuweather.url}${service.accuweather.path.currentConditions}")
    private String currentConditionsUrl;
    @Value("${service.accuweather.url}${service.accuweather.path.forecasts}")
    private String forecastsUrl;

    @Override
    public ResponseEntity<List<AccuLocationDetails>> getLocation(String postalCode) {
        UriComponentsBuilder builder = basicBuilder(postalCodeSearchUrl).queryParam(QUERY_PARAM, postalCode);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccuLocationDetails>>() {}
        );
    }

    @Override
    public ResponseEntity<List<AccuWeather>> getCurrentConditions(String locationKey) {
        Map<String, String> urlParams = getLocationKeyUrlParams(locationKey);
        UriComponentsBuilder builder = basicBuilder(currentConditionsUrl).queryParam(METRIC_PARAM, metric);

        return restTemplate.exchange(
                builder.buildAndExpand(urlParams).toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccuWeather>>() {}
        );
    }

    @Override
    public ResponseEntity<AccuForecast> getForecasts(String locationKey) {
        Map<String, String> urlParams = getLocationKeyUrlParams(locationKey);
        UriComponentsBuilder builder = basicBuilder(forecastsUrl).queryParam(METRIC_PARAM, metric);

        return restTemplate.getForEntity(
                builder.buildAndExpand(urlParams).toUriString(),
                AccuForecast.class
        );
    }

    private UriComponentsBuilder basicBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(API_KEY_PARAM, apiKey)
                .queryParam(LANGUAGE_PARAM, language);
    }

    private Map<String, String> getLocationKeyUrlParams(String locationKey) {
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put(LOCATION_KEY_PARAM, locationKey);
        return urlParams;
    }

}
