package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("current") val currentWeather: WeatherDto,
    @SerializedName("forecast") val forecastList: ForecastList,
)
