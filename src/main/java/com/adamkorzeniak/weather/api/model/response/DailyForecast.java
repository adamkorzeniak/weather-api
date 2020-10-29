package com.adamkorzeniak.weather.interfaces.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DailyForecast {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("temperature")
    private Temperature temperature;

    @JsonProperty("weather")
    private WeatherInfo weather;
}
