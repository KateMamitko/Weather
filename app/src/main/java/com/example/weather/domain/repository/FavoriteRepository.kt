package com.example.weather.domain.repository

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun favoriteCityList(): Flow<List<City>>
    fun observeIsFavorite(cityId: String): Flow<Boolean>
    suspend fun addToFavList(city: City)
    suspend fun deleteFromFavList(cityId: String)

}