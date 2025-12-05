package com.example.weather.presentation.favorite

import com.example.weather.domain.entety.City

sealed interface FavoriteSideEffect {
    data object ClicksSearchCity : FavoriteSideEffect
    data object ClickAddFavorite : FavoriteSideEffect
    data class OpenDetailsInfo(val city: City) : FavoriteSideEffect
    data class ShowErrorMessage(val str: String) : FavoriteSideEffect
}