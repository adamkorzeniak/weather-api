package com.adamkorzeniak.weather;


import com.adamkorzeniak.weather.api.model.response.DailyForecast;
import com.adamkorzeniak.weather.api.model.response.Temperature;
import com.adamkorzeniak.weather.api.model.response.WeatherInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-test")
public class IntegrationTests {

    private static final double MAX_VALID_TEMPERATURE = 150.0;
    private static final double MIN_VALID_TEMPERATURE = -150.0;
    private static final String VALID_TEMPERATURE_UNIT = "C";

    private static final String CURRENT_CONDITION_PATH = "/api/v0/weather/current?postalCode=";
    private static final String FORECAST_PATH = "/api/v0/weather/forecast?postalCode=";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void GetCurrentCondition_NoIssues_ReturnsCurrentConditions() throws Exception {

        String postalCode = "87-890";
        String currentDate = LocalDate.now().toString();
        String currentConditionPath = CURRENT_CONDITION_PATH + postalCode;

        mockMvc.perform(get(currentConditionPath))
                .andExpect(status().isOk())
                .andExpect(header().exists("RateLimit"))
                .andExpect(header().exists("RateLimit"))
                .andExpect(jsonPath("$.date", startsWith(currentDate)))
                .andExpect(jsonPath("$.temperature", notNullValue()))
                .andExpect(jsonPath("$.temperature.value", greaterThan(MIN_VALID_TEMPERATURE)))
                .andExpect(jsonPath("$.temperature.value", lessThan(MAX_VALID_TEMPERATURE)))
                .andExpect(jsonPath("$.temperature.unit", is(VALID_TEMPERATURE_UNIT)))
                .andExpect(jsonPath("$.temperature.temperatureText", matchesPattern("^-?\\d+\\.?\\d*C$")))
                .andExpect(jsonPath("$.weather", notNullValue()))
                .andExpect(jsonPath("$.weather.description", notNullValue()))
                .andExpect(jsonPath("$.weather.precipitation").isBoolean());
    }

    @Test
    public void GetForecast_NoIssues_ReturnsForecast() throws Exception {

        String postalCode = "66-614";
        String forecastPath = FORECAST_PATH + postalCode;

        MvcResult result = mockMvc.perform(get(forecastPath))
                .andExpect(status().isOk())
                .andExpect(header().exists("RateLimit"))
                .andExpect(header().exists("RateLimitRemaining"))
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        List<DailyForecast> forecasts = Arrays.asList(mapper.readValue(contentAsString, DailyForecast[].class));

        for (int i = 0; i < forecasts.size(); i++) {
            DailyForecast forecast = forecasts.get(i);
            Temperature temperature = forecast.getTemperature();
            WeatherInfo weather = forecast.getWeather();
            String forecastDate = LocalDate.now().plusDays(i).toString();

            assertThat(buildMessage(i, "date"), forecast.getDate().toString(), startsWith(forecastDate));
            assertThat(buildMessage(i, "temperature"), temperature, notNullValue());
            assertThat(buildMessage(i, "temperature.minimum"), temperature.getMinimum(), greaterThan(MIN_VALID_TEMPERATURE));
            assertThat(buildMessage(i, "temperature.minimum"), temperature.getMinimum(), lessThan(MAX_VALID_TEMPERATURE));
            assertThat(buildMessage(i, "temperature.maximum"), temperature.getMaximum(), greaterThan(MIN_VALID_TEMPERATURE));
            assertThat(buildMessage(i, "temperature.maximum"), temperature.getMaximum(), lessThan(MAX_VALID_TEMPERATURE));
            assertThat(buildMessage(i, "temperature.unit"), temperature.getUnit(), is(VALID_TEMPERATURE_UNIT));
            assertThat(buildMessage(i, "temperature.minimumTemperatureText"), temperature.getMinimumTemperatureText(), is(buildTemperatureText(temperature.getMinimum())));
            assertThat(buildMessage(i, "temperature.maximumTemperatureText"), temperature.getMaximumTemperatureText(), is(buildTemperatureText(temperature.getMaximum())));
            assertThat(buildMessage(i, "weather"), weather, notNullValue());
            assertThat(buildMessage(i, "weather.dayDescription"), weather.getDayDescription(), notNullValue());
            assertThat(buildMessage(i, "weather.dayPrecipitation"), weather.getDayPrecipitation(), notNullValue());
            assertThat(buildMessage(i, "weather.nightDescription"), weather.getNightDescription(), notNullValue());
            assertThat(buildMessage(i, "weather.nightPrecipitation"), weather.getNightPrecipitation(), notNullValue());
        }
    }

    private String buildMessage(int i, String field) {
        return String.format("Daily Forecast assertion failed for element: %d, field: %s ", i, field);
    }

    private String buildTemperatureText(Double temperatureValue) {
        return temperatureValue + VALID_TEMPERATURE_UNIT;
    }

}
