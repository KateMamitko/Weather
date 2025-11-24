package com.example.weather.domain.usecase

import com.example.weather.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteCityListUseCase  @Inject constructor(val favoriteRepository: FavoriteRepository) {
    operator fun invoke() = favoriteRepository.favoriteCityList()
}