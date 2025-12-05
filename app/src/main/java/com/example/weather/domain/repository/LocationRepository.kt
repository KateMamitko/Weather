package com.example.weather.domain.repository

import android.location.Location
import com.example.weather.domain.entety.MyLocationWeather

interface LocationRepository {

    suspend fun getCurrentLocation(): Location?
    suspend fun getWeatherByCoordinates(a: Double, b: Double): MyLocationWeather
}