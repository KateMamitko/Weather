package com.example.weather.domain.usecase

import com.example.weather.domain.repository.FavoriteRepository
import javax.inject.Inject

class ObserveIsFavoriteUseCase  @Inject constructor(val favoriteRepository: FavoriteRepository) {
    operator fun invoke(id: Int) = favoriteRepository.observeIsFavorite(id)
}