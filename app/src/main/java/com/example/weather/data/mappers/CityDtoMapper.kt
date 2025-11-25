package com.example.weather.data.mappers

import com.example.weather.data.network.dto.CityDto
import com.example.weather.domain.entety.City


fun CityDto.toDomain() = City(id, cityName = name, country)