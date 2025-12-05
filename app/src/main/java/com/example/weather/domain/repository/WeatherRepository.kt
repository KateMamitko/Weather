package com.example.weather.domain.repository

import com.example.weather.domain.entety.Forecast
import com.example.weather.domain.entety.Weather

interface WeatherRepository {

    suspend fun getForecast(city: String): Forecast
    suspend fun getWeather(idCity: String): Weather
}