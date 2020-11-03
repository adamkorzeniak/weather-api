package com.adamkorzeniak.weather.api.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Temperature {

    @JsonProperty("minimum")
    private Double minimum;

    @JsonProperty("maximum")
    private Double maximum;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("minimumTemperatureText")
    private String minimumTemperatureText;

    @JsonProperty("maximumTemperatureText")
    private String maximumTemperatureText;
}
