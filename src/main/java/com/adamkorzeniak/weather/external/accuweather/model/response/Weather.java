package com.adamkorzeniak.weather.external.accuweather.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    //TODO: Consider removing and leave epochDate
    @JsonProperty("LocalObservationDateTime")
    private LocalDateTime localDateTime;

    //TODO: Consider changing to Instant
    @JsonProperty("EpochDate")
    private Long epochDate;

    @JsonProperty("WeatherText")
    private String weatherText;

    @JsonProperty("HasPrecipitation")
    private Boolean hasPrecipitation;

    @JsonProperty("Temperature")
    private Temperature temperature;
}
