package com.adamkorzeniak.weather.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class CallRateLimits {

    @JsonProperty("date")
    private OffsetDateTime date;

    @JsonProperty("rateLimit")
    private Long rateLimit;

    @JsonProperty("remainingRateLimit")
    private Long remainingRateLimit;
}
