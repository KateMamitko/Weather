package com.example.weather.domain.usecase

import com.example.weather.domain.repository.LocationRepository
import javax.inject.Inject

class GetWeatherByCoordinatesUseCase @Inject constructor(val locationRepository: LocationRepository) {

    suspend operator fun invoke(a: Double, b: Double) =
        locationRepository.getWeatherByCoordinates(a, b)
}