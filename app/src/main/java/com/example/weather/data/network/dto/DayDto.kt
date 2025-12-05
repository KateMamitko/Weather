package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("avgtemp_c") val temperature: Float,
    @SerializedName("condition") val condition: Condition
)