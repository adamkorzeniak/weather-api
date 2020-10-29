package com.adamkorzeniak.weather.external.accuweather.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemperatureMetric {

    @JsonProperty("Value")
    private Double value;

    //TODO: Consider changing to enum, list of units is in documentation
    @JsonProperty("Unit")
    private String unit;
}
