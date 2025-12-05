package com.example.weather.data.network

import com.example.weather.data.network.dto.CityDto
import com.example.weather.data.network.dto.CurrentWeatherDto
import com.example.weather.data.network.dto.LocationDto
import com.example.weather.data.network.dto.WeatherCurrentDto
import com.example.weather.domain.entety.City
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json")
    suspend fun loadForecastList(
        @Query("q") q: City,
        @Query("days") days: Int = 4
    ): CurrentWeatherDto

    @GET("current.json")
    suspend fun currentWeather(@Query("q") q: String): WeatherCurrentDto

    @GET("search.json")
    suspend fun searchCity(@Query("q") q: String): List<CityDto>


    @GET("current.json")
    suspend fun getMyLocationWeather(@Query("q") q: String): LocationDto
}