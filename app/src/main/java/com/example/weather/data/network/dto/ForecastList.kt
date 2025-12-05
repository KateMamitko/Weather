package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastList(
    @SerializedName("forecastday") val listWeather: List<ForecastDto>
)
