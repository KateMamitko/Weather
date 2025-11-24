package com.example.weather.domain.usecase

import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherUseCase  @Inject constructor(val weatherRepository: WeatherRepository) {
    suspend operator fun invoke(id: Int) = weatherRepository.getWeather(id)
}