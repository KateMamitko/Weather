package com.example.weather.data.impl

import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteRepositoryImpl : FavoriteRepository {
    override fun favoriteCityList(): Flow<City> {

    }

    override fun observeIsFavorite(cityId: Int): Flow<Boolean> {
    }

    override suspend fun addToFavList(cityId: Int) {
    }

    override suspend fun deleteFromFavList(cityId: Int) {
    }
}