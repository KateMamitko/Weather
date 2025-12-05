package com.example.weather.domain.usecase

import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase  @Inject constructor(val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(city: String) = weatherRepository.getForecast(city)
}