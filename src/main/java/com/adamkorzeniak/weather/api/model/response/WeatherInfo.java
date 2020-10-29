package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherInfo {

    @JsonProperty("dayDescription")
    private String dayDescription;

    @JsonProperty("dayPrecipitation")
    private Boolean dayPrecipitation;

    @JsonProperty("nightDescription")
    private String nightDescription;

    @JsonProperty("nightPrecipitation")
    private Boolean nightPrecipitation;
}
