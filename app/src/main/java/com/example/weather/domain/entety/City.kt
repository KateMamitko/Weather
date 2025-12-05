package com.example.weather.domain.entety

import kotlinx.serialization.Serializable


@Serializable
data class City(val id: String, val cityName: String, val country: String)