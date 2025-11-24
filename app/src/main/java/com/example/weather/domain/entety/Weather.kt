package com.example.weather.domain.entety

import android.icu.util.Calendar

data class Weather(
    val city: String,
    val temperature: Float,
    val description: String,
    val conditionUrl: String,
    val isFavorite: Boolean,
    val date: Calendar
)
