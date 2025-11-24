package com.example.weather.domain.entety

data class Forecast(
    val currentWeather: Weather,
    val upcoming: List<Weather>
)
