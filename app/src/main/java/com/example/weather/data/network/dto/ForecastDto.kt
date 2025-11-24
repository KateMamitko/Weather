package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName ("date_epoch") val date_epoch: Long,
    @SerializedName ("day") val dayDto: DayDto
)
