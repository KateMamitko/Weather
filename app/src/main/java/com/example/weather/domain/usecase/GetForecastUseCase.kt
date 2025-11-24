package com.example.weather.domain.usecase

import com.example.weather.domain.entety.City
import com.example.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetForecastUseCase  @Inject constructor(val weatherRepository: WeatherRepository) {
    operator fun invoke(city: City) = weatherRepository.getForecast(city)
}