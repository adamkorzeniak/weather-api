# Weather API

REST API providing details about current weather and forecasts for postal code.

## Table of Contents

- [General Info](#general-info)
- [Setup](#setup)
  - [Configuration](#configuration)
  - [Build](#build)
  - [Deployment](#deployment)
- [Usage](#usage)
  - [Documentation](#documentation)
  - [Examples](#examples)
- [Technologies](#technologies)

## General Info

Features:
- Get current weather conditions for postal code
- Get 5 days forecast for postal code
- Each response returns headers with information of what is daily and remaining limit of available calls to AccuWeather Service.

## Setup

### Configuration

Under `src/main/resources` there are placed files for application configuration. `application.yml` configures properties which are common for all environments. 
There are also files `application-XXX.yml` which will be picked up only for specific environment.

In file `application.yml` there is a property `spring.profiles.active` which is by default set to `local` environment and will load by default `application-local.yml` property file.
It is possible to change value of `spring.profiles.active` and include by default other environment property file.

During deployment you can specify which environment property file should be included by providing option `-Dspring.profiles.active=XXX`.
If this option is not provided, default environemnt property will be loaded.

It is also important to change `service.accuweather.apiKey` property. 
Value provided in this repository only allows for 50 AccuWeather calls daily which within current implementation translates to 25 current conditions or forecast calls.

### Build

- Open command line and change directory to directory of project
- Run `mvn clean package` to package application to JAR file. Optionally use `-DskipTests=true` if you do not want to run integration tests.

Run to package code and execute integration tests
```
mvn clean package
```

Run to package code and skip integration tests:
```
mvn clean package -DskipTests=true
```

- JAR File will be generated under `target` directory and should be named `weather-api-0.1.0.jar`. This file can be used to deploy application.

### Deployment

- Open command line and change directory to directory where `weather-api-0.1.0.jar` is located.
- Run `java -jar weather-api-0.1.0.jar` to start application. 
You can provide option `-Dspring.profiles.active=XXX` to declare which environment properties should be included (i.e `-Dspring.profiles.active=XXX` for production deployment).

Default local deployment is currently configured to require mocks running on `http://localhost:8999`

Run to deploy application using default property configuration
```
java -jar weather-api-0.1.0.jar
```

Run to deploy application using declared property configuration
```
java -jar -Dspring.profiles.active=prod weather-api-0.1.0.jar
```

## Usage

### Documentation

You can find swagger file API documentation under `docs/swagger.yaml`

### Examples

You can find Postman collection with request examples under `docs/request-examples/Weather API.postman_collection.json`. You can import it to Postman using `File->Import` option.

Example requests that can be send from machine on which application was deployed. Otherwise change host and port if needed.

#### Get Current Conditions

http://localhost:8080/api/v0/weather/current?postalCode=00-001

#### Get 5 Day Forecast

http://localhost:8080/api/v0/weather/forecast?postalCode=00-001

### Response Headers

Response contains headers `RateLimit` and `RateLimitRemaining` which inform about daily limit and remaining daily limit of AccuWeather Service API calls.
Those don't equal to number of requests to this API as one request generates one or more AccuWeather API requests.

### Running Integration tests

- Open command line and change directory to directory of project
- Run `mvn test` start integration tests. 

Keep in mind that integration tests might fail if connection to AccuWeather is not working or daily call limits were exceeded.

You can change profile (which will load related property file) for running integration tests by changing value of `org.springframework.test.context.ActiveProfiles` annotation for `com.adamkorzeniak.weather.IntegrationTests` class


## Technologies

- Java 8
- Spring Boot 2.3.4
- JUnit 5
- Lombok