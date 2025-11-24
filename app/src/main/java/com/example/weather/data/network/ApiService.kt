package com.example.weather.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("forecast.json?key=5cd530c35240416595c161857252411&q=London&days=1&aqi=no&alerts=no")
    fun getForecastForCity(
        @Query("q") q: String,
        @Query("days") days: String
    )

}