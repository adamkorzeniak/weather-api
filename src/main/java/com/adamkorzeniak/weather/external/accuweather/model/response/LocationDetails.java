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
//TODO: Consider extending City and renaming classes
public class LocationDetails extends Location {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Rank")
    private Integer rank;

    @JsonProperty("LocalizedName")
    private String localizedName;

    @JsonProperty("EnglishName")
    private String englishName;

    @JsonProperty("Country")
    private Region country;

    @JsonProperty("AdministrativeArea")
    private Region administrativeArea;

    @JsonProperty("ParentCity")
    private Location parentCity;
}
