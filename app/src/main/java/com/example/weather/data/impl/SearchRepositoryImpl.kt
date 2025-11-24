package com.example.weather.data.impl

import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.SearchRepository

class SearchRepositoryImpl : SearchRepository {
    override suspend fun search(cityName: String): City {
        TODO("Not yet implemented")
    }
}