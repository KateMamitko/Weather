package com.example.weather.domain.usecase

import com.example.weather.domain.repository.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(val locationRepository: LocationRepository) {

    suspend operator fun invoke() = locationRepository.getCurrentLocation()
}