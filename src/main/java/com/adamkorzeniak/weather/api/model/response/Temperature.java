package com.adamkorzeniak.weather.interfaces.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temperature {

    @JsonProperty("minimum")
    private Double minimum;

    @JsonProperty("maximum")
    private Double maximum;

    @JsonProperty("unit")
    private String unit;

    @JsonProperty("minimumTemperatureText")
    private Double minimumTemperatureText;

    @JsonProperty("maximumTemperatureText")
    private Double maximumTemperatureText;
}
