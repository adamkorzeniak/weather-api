package com.adamkorzeniak.weather.api.model;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DataWithContext<T> {

    @Setter(AccessLevel.NONE)
    private final T data;

    private String rateLimit;

    private String remainingRateLimit;

}
