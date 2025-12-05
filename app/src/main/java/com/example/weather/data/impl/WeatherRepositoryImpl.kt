package com.example.weather.data.impl

import com.example.weather.data.mappers.toDomain
import com.example.weather.data.mappers.toWeather
import com.example.weather.data.network.ApiService
import com.example.weather.domain.entety.Forecast
import com.example.weather.domain.entety.Weather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    WeatherRepository {
    override suspend fun getForecast(city: String): Forecast {
        return apiService.loadForecastList(city).toDomain()
    }

    override suspend fun getWeather(idCity: String): Weather {
        return apiService.currentWeather(idCity).toWeather()
    }
}