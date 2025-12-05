package com.example.weather.data.impl

import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(): SearchRepository {
    override suspend fun search(cityName: String): City {
        TODO("Not yet implemented")
    }
}