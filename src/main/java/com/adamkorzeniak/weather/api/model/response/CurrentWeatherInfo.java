package com.adamkorzeniak.weather.interfaces.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentWeatherInfo {

    @JsonProperty("description")
    private String description;

    @JsonProperty("precipitation")
    private Boolean precipitation;
}
