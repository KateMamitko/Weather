package com.example.weather.domain.entety

import java.util.Calendar


data class Weather(
    val temperature: Float,
    val description: String,
    val conditionUrl: String,
    val isFavorite: Boolean,
    val date: Calendar
)
