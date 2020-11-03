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
public class AccuLocationDetails extends AccuLocation {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Rank")
    private Integer rank;

    @JsonProperty("Country")
    private AccuRegion country;

    @JsonProperty("AdministrativeArea")
    private AccuRegion administrativeArea;

    @JsonProperty("ParentCity")
    private AccuLocation parentCity;
}
