package com.example.weather.domain.repository

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun favoriteCityList(): Flow<City>
    fun observeIsFavorite(cityId: Int): Flow<Boolean>
    suspend fun addToFavList(cityId: Int)
    suspend fun deleteFromFavList(cityId: Int)

}