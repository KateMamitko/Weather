package com.example.weather.data.impl

import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl : FavoriteRepository {
    override fun favoriteCityList(): Flow<City> {
        return flow {

        }
    }

    override fun observeIsFavorite(cityId: String): Flow<Boolean> {
        return flow {

        }
    }

    override suspend fun addToFavList(cityId: String) {
    }

    override suspend fun deleteFromFavList(cityId: String) {
    }
}