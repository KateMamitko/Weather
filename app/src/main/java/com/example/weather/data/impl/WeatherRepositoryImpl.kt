package com.example.weather.data.impl

import com.example.weather.domain.entety.City
import com.example.weather.domain.entety.Forecast
import com.example.weather.domain.entety.Weather
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(): WeatherRepository {
    override fun getForecast(city: City): Forecast {
        TODO("Not yet implemented")
    }

    override suspend fun getWeather(idCity: String): Weather {
        TODO("Not yet implemented")
    }
}