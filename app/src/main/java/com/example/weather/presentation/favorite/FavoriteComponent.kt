package com.example.weather.presentation.favorite

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {

    val model: StateFlow<FavoriteState>

    fun onClickSearch()
    fun onClickAddFavorite()
    fun clickToItemCity(city: City)
}