package com.example.weather.domain.repository

import com.example.weather.domain.entety.City

interface SearchRepository {
    suspend fun search(cityName: String): City

}