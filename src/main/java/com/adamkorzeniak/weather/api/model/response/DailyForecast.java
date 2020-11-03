package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DailyForecast {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("temperature")
    private Temperature temperature;

    @JsonProperty("weather")
    private WeatherInfo weather;
}
