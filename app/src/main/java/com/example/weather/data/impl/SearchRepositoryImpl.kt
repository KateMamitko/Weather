package com.example.weather.data.impl

import com.example.weather.data.mappers.toDomain
import com.example.weather.data.network.ApiService
import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    SearchRepository {
    override suspend fun search(cityName: String): List<City> {
        return apiService.searchCity(cityName).map {
            it.toDomain()
        }
    }
}