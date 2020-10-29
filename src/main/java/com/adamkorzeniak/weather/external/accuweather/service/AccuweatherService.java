package com.adamkorzeniak.weather.external.accuweather.service;

import com.adamkorzeniak.weather.api.model.response.CallRateLimits;

public interface AccuweatherService {

    CallRateLimits getCallLimits();
}
