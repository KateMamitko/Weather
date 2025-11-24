package com.example.weather.domain.repository

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun favoriteCityList(): Flow<City>
    fun observeIsFavorite(cityId: String): Flow<Boolean>
    suspend fun addToFavList(cityId: String)
    suspend fun deleteFromFavList(cityId: String)

}