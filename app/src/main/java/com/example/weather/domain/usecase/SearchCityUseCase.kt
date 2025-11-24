package com.example.weather.domain.usecase

import com.example.weather.domain.repository.SearchRepository
import javax.inject.Inject

class SearchCityUseCase  @Inject constructor(val searchRepository: SearchRepository) {
    suspend operator fun invoke(cityName: String) = searchRepository.search(cityName)
}