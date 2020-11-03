package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CurrentWeatherInfo {

    @JsonProperty("description")
    private String description;

    @JsonProperty("precipitation")
    private Boolean precipitation;
}
