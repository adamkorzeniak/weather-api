package com.adamkorzeniak.weather.interfaces.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class AvailableCalls {

    @JsonProperty("date")
    private OffsetDateTime date;

    @JsonProperty("rateLimit")
    private String rateLimit;

    @JsonProperty("remainingRateLimit")
    private String remainingRateLimit;
}
