package com.adamkorzeniak.weather;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class IntegrationTest {

    private static final String CURRENT_CONDITION_PATH = "/api/v0/weather/current?postalCode=";
    private static final String FORECAST_PATH = "/api/v0/weather/forecast?postalCode=";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetCurrentCondition_NoIssues_ReturnsCurrentConditions() throws Exception {

        String currentDate = LocalDate.now().toString();
        String postalCode = "00-001";
        String currentConditionPath = CURRENT_CONDITION_PATH + postalCode;

        mockMvc.perform(get(currentConditionPath))
                .andExpect(status().isOk())
                .andExpect(header().exists("rateLimit"))
                .andExpect(header().exists("rateLimitRemaining"))
                .andExpect(jsonPath("$.date", startsWith(currentDate)))
                .andExpect(jsonPath("$.temperature", notNullValue()))
                .andExpect(jsonPath("$.temperature.value", greaterThan(-150.0)))
                .andExpect(jsonPath("$.temperature.value", lessThan(150.0)))
                .andExpect(jsonPath("$.temperature.unit", is("C")))
                .andExpect(jsonPath("$.temperature.temperatureText", matchesPattern("^-?\\d+\\.?\\d*C$")))
                .andExpect(jsonPath("$.weather", notNullValue()))
                .andExpect(jsonPath("$.weather.description", notNullValue()))
                .andExpect(jsonPath("$.weather.precipitation").isBoolean());

    }

    @Test
    public void GetForecast_NoIssues_ReturnsForecast() throws Exception {

        String postalCode = "25-001";
        String forecastPath = FORECAST_PATH + postalCode;

        mockMvc.perform(get(forecastPath))
                .andExpect(status().isOk())
                .andExpect(header().exists("rateLimit"))
                .andExpect(header().exists("rateLimitRemaining"))
                .andExpect(jsonPath("$[*]", hasSize(4)))
                .andExpect(jsonPath("$[*].date", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].temperature", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].temperature.minimum", allOf(greaterThan(-150.0))))
                .andExpect(jsonPath("$[*].temperature.maximum", allOf(greaterThan(-150.0))))
                .andExpect(jsonPath("$[*].temperature.minimum", allOf(lessThan(150.0))))
                .andExpect(jsonPath("$[*].temperature.maximum", allOf(lessThan(150.0))))
                .andExpect(jsonPath("$[*].temperature.minimumTemperatureText", matchesPattern("^-?\\d+\\.?\\d*C$")))
                .andExpect(jsonPath("$[*].temperature.maximumTemperatureText", matchesPattern("^-?\\d+\\.?\\d*C$")))
                .andExpect(jsonPath("$[*].temperature.unit", allOf(is("C"))))
                .andExpect(jsonPath("$[*].weather", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].weather.dayDescription", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].weather.nightDescription", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].weather.dayPrecipitation", allOf(notNullValue())))
                .andExpect(jsonPath("$[*].weather.nightPrecipitation", allOf(notNullValue())));
    }

}
