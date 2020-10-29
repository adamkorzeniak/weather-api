package com.adamkorzeniak.weather.interfaces.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Weather {

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("temperature")
    private CurrentTemperature temperature;

    @JsonProperty("weather")
    private CurrentWeatherInfo weather;
}
