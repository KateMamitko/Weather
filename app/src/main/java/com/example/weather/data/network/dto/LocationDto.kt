package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("location") val location: CityDto,
    @SerializedName("current") val currentWeather: WeatherDto
    )
