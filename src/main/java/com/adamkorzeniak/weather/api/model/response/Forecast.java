package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Forecast {

    @JsonProperty("dailyForecasts")
    private List<DailyForecast> dailyForecasts;
}
