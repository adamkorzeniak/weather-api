package com.adamkorzeniak.weather.external.accuweather.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecast {

    //TODO: Consider removing and leave epochDate
    @JsonProperty("Date")
    private OffsetDateTime date;

    //TODO: COnsider changing to Instant
    @JsonProperty("EpochDate")
    private Long epochDate;

    @JsonProperty("Temperature")
    private Temperatures temperature;

    @JsonProperty("Day")
    private WeatherInfo day;

    @JsonProperty("Night")
    private WeatherInfo night;

}
