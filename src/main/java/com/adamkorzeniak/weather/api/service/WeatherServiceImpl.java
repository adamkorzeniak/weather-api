package com.adamkorzeniak.weather.api.service;

import com.adamkorzeniak.weather.api.model.DataWithContext;
import com.adamkorzeniak.weather.api.model.response.*;
import com.adamkorzeniak.weather.external.accuweather.exception.AccuWeatherEmptyResponseException;
import com.adamkorzeniak.weather.external.accuweather.exception.AccuWeatherResponseMissingContentException;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuDailyForecast;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuForecast;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuLocationDetails;
import com.adamkorzeniak.weather.external.accuweather.model.response.AccuWeather;
import com.adamkorzeniak.weather.external.accuweather.service.AccuWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String SEARCH_BY_POSTAL_CODE_DESC = "Search By Postal Code";
    private static final String GET_CURRENT_CONDITIONS_DESC = "Get Current Conditions";
    private static final String GET_FORECAST_DESC = "Get Forecast";

    private final AccuWeatherService accuWeatherService;

    @Value("${service.accuweather.header.rate-limit}")
    private String rateLimitHeaderName;
    @Value("${service.accuweather.header.remaining-rate-limit}")
    private String remainingRateLimitHeaderName;

    @Autowired
    public WeatherServiceImpl(AccuWeatherService accuWeatherService) {
        this.accuWeatherService = accuWeatherService;
    }

    @Override
    public DataWithContext<Weather> getCurrentConditions(String postalCode) {
        String locationKey = getLocationKey(postalCode);
        DataWithContext<AccuWeather> weather = getWeather(locationKey);
        return convertToWeatherDTO(weather);
    }

    private String getLocationKey(String postalCode) {
        ResponseEntity<List<AccuLocationDetails>> response = accuWeatherService.getLocation(postalCode);
        if (response.getBody() == null) {
            throw new AccuWeatherEmptyResponseException(SEARCH_BY_POSTAL_CODE_DESC);
        }

        return response.getBody().stream()
                .max(Comparator.comparing(AccuLocationDetails::getRank))
                .map(AccuLocationDetails::getKey)
                .orElseThrow(
                        () -> new AccuWeatherResponseMissingContentException(SEARCH_BY_POSTAL_CODE_DESC, "field key"));
    }

    private DataWithContext<AccuWeather> getWeather(String locationKey) {
        ResponseEntity<List<AccuWeather>> response = accuWeatherService.getCurrentConditions(locationKey);
        if (response.getBody() == null) {
            throw new AccuWeatherEmptyResponseException(GET_CURRENT_CONDITIONS_DESC);
        }

        AccuWeather weather = response.getBody()
                .stream()
                .findFirst()
                .orElseThrow(
                        () -> new AccuWeatherResponseMissingContentException(GET_CURRENT_CONDITIONS_DESC, "any elements"));

        DataWithContext<AccuWeather> result = new DataWithContext<>(weather);
        enrichHeaders(result, response.getHeaders());
        return result;
    }

    private DataWithContext<Weather> convertToWeatherDTO(DataWithContext<AccuWeather> weather) {
        AccuWeather data = weather.getData();

        Weather result = new Weather();
        result.setDate(Instant.ofEpochSecond(data.getEpochTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        CurrentTemperature temperature = new CurrentTemperature();
        temperature.setValue(data.getTemperature().getTemperatureMetric().getValue());
        temperature.setUnit(data.getTemperature().getTemperatureMetric().getUnit());
        temperature.setTemperatureText(buildTemperatureText(temperature.getValue(), temperature.getUnit()));
        result.setTemperature(temperature);

        CurrentWeatherInfo weatherInfo = new CurrentWeatherInfo();
        weatherInfo.setDescription(data.getWeatherText());
        weatherInfo.setPrecipitation(data.getHasPrecipitation());
        result.setWeatherInfo(weatherInfo);

        DataWithContext<Weather> weatherWithContext = new DataWithContext<>(result);
        weatherWithContext.setRateLimit(weather.getRateLimit());
        weatherWithContext.setRemainingRateLimit(weather.getRemainingRateLimit());
        return weatherWithContext;
    }

    private String buildTemperatureText(Double value, String unit) {
        return value + unit;
    }

    @Override
    public DataWithContext<List<DailyForecast>> getForecast(String postalCode) {
        String locationKey = getLocationKey(postalCode);
        DataWithContext<AccuForecast> forecast = getForecastDetails(locationKey);
        return convertToForecastDTO(forecast);
    }

    private DataWithContext<AccuForecast> getForecastDetails(String locationKey) {
        ResponseEntity<AccuForecast> response = accuWeatherService.getForecasts(locationKey);
        if (response.getBody() == null) {
            throw new AccuWeatherEmptyResponseException(GET_FORECAST_DESC);
        }

        DataWithContext<AccuForecast> result = new DataWithContext<>(response.getBody());
        enrichHeaders(result, response.getHeaders());
        return result;
    }

    private DataWithContext<List<DailyForecast>> convertToForecastDTO(DataWithContext<AccuForecast> forecast) {
        AccuForecast data = forecast.getData();

        List<DailyForecast> result = data.getDailyForecasts().stream()
                .map(this::buildDailyForecast)
                .collect(Collectors.toList());

        DataWithContext<List<DailyForecast>> forecastWithContext = new DataWithContext<>(result);
        forecastWithContext.setRateLimit(forecast.getRateLimit());
        forecastWithContext.setRemainingRateLimit(forecast.getRemainingRateLimit());
        return forecastWithContext;
    }

    private DailyForecast buildDailyForecast(AccuDailyForecast accuForecast) {
        DailyForecast forecast = new DailyForecast();
        forecast.setDate(Instant.ofEpochSecond(accuForecast.getEpochDate()).atZone(ZoneId.systemDefault()).toLocalDate());

        Temperature temperature = new Temperature();
        temperature.setMaximum(accuForecast.getTemperature().getMaximum().getValue());
        temperature.setMinimum(accuForecast.getTemperature().getMinimum().getValue());
        temperature.setUnit(accuForecast.getTemperature().getMaximum().getUnit());
        temperature.setMaximumTemperatureText(buildTemperatureText(temperature.getMaximum(), temperature.getUnit()));
        temperature.setMinimumTemperatureText(buildTemperatureText(temperature.getMinimum(), temperature.getUnit()));
        forecast.setTemperature(temperature);

        WeatherInfo weather = new WeatherInfo();
        weather.setDayDescription(accuForecast.getDay().getIconPhrase());
        weather.setDayPrecipitation(accuForecast.getDay().getHasPrecipitation());
        weather.setNightDescription(accuForecast.getNight().getIconPhrase());
        weather.setNightPrecipitation(accuForecast.getNight().getHasPrecipitation());
        forecast.setWeather(weather);

        return forecast;
    }

    private void enrichHeaders(DataWithContext<?> response, HttpHeaders headers) {
        response.setRateLimit(getHeaderValue(headers, rateLimitHeaderName));
        response.setRemainingRateLimit(getHeaderValue(headers, remainingRateLimitHeaderName));
    }

    private String getHeaderValue(HttpHeaders headers, String headerName) {
        return headers.getOrEmpty(headerName)
                .stream()
                .findFirst()
                .orElse(null);
    }

}
