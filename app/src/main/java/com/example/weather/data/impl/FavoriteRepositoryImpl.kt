package com.example.weather.data.impl

import com.example.weather.data.local.FavoriteCityDao
import com.example.weather.data.mappers.CityMapper
import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteCityDao,
    private val mapper: CityMapper
) : FavoriteRepository {

    override fun favoriteCityList(): Flow<List<City>> {
        return dao
            .getFavoriteCity()
            .let { mapper.run { it.toDomain() } }
    }

    override fun observeIsFavorite(cityId: String): Flow<Boolean> {
        return dao.observeIsFollowing(cityId)
    }

    override suspend fun addToFavList(city: City) {
        val cityDbModel = mapper.run {
            city.toDb()
        }
        dao.addFavoriteCity(cityDbModel)
    }

    override suspend fun deleteFromFavList(cityId: String) {
        dao.deleteFavoriteCity(cityId)
    }
}