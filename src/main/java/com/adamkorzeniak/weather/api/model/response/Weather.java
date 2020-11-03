package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Weather {

    @JsonProperty("date")
    private LocalDateTime date;

    @JsonProperty("temperature")
    private CurrentTemperature temperature;

    @JsonProperty("weather")
    private CurrentWeatherInfo weatherInfo;
}
