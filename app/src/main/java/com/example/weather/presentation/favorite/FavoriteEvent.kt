package com.example.weather.presentation.favorite

import com.example.weather.domain.entety.City


sealed interface FavoriteEvent {
    data object ClicksSearchCity : FavoriteEvent
    data object ClickAddFavorite : FavoriteEvent
    data class OpenDetailsInfo(val city: City) : FavoriteEvent
    data class StartLoading(val city: City) : FavoriteEvent
    data object GetLocation : FavoriteEvent
}