package com.example.weather.presentation.favorite

import com.example.weather.domain.entety.City


data class FavoriteState(
    val cityItem: List<CityItem>
) {

    data class CityItem(val city: City, val weatherState: WeatherState)
    sealed interface WeatherState {

        data object Loading : WeatherState

        data class Complete(
            val temperature: Float,
            val icon: String
        ) : WeatherState

        data object Error : WeatherState
    }
}

