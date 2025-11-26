package com.example.weather.data.network

import com.example.weather.data.network.dto.CityDto
import com.example.weather.data.network.dto.CurrentWeatherDto
import com.example.weather.data.network.dto.WeatherCurrentDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("current.json")
    suspend fun loadForecastList(
        @Query("q") q: String,
        @Query("days") days: Int = 4
    ): CurrentWeatherDto

    @GET("forecast.json")
    suspend fun currentWeather(@Query("q") q: String): WeatherCurrentDto

    @GET("search.json")
    suspend fun searchCity(@Query("q") q: String): List<CityDto>
}