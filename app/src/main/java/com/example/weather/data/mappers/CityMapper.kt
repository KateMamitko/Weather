package com.example.weather.data.mappers

import com.example.weather.data.local.model.CityDbModel
import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CityMapper @Inject constructor() {


    fun CityDbModel.toDomain() = City(
        id = id,
        cityName = cityName,
        country = country
    )

    fun City.toDb() = CityDbModel(
        id = id,
        cityName = cityName,
        country = country
    )

    fun Flow<List<CityDbModel>>.toDomain(): Flow<List<City>> =
        map { list -> list.map { it.toDomain() } }
}