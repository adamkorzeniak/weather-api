package com.adamkorzeniak.weather.external.accuweather.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccuDailyForecast {

    @JsonProperty("EpochDate")
    private Long epochDate;

    @JsonProperty("Temperature")
    private AccuTemperatures temperature;

    @JsonProperty("Day")
    private AccuWeatherInfo day;

    @JsonProperty("Night")
    private AccuWeatherInfo night;

}
