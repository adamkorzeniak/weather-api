package com.adamkorzeniak.weather.api.service;

import com.adamkorzeniak.weather.api.model.DataWithContext;
import com.adamkorzeniak.weather.api.model.response.DailyForecast;
import com.adamkorzeniak.weather.api.model.response.Weather;

import java.util.List;

public interface WeatherService {

    DataWithContext<Weather> getCurrentConditions(String postalCode);

    DataWithContext<List<DailyForecast>> getForecast(String postalCode);
}
