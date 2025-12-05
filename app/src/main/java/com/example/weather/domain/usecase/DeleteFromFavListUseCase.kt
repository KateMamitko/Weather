package com.example.weather.domain.usecase

import com.example.weather.domain.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFromFavListUseCase  @Inject constructor(val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(id: String) = favoriteRepository.deleteFromFavList(id)
}