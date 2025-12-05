package com.example.weather.presentation.favorite


data class FavoriteState(
    val cityItem: CityItem
) {

    data class CityItem(val cityName: String, val weatherState: WeatherState)
    sealed interface WeatherState {
        data object Initial : WeatherState
        data object Loading : WeatherState

        data class Complete(
            val temperature: Float,
            val icon: String
        ) : WeatherState

        data object Error : WeatherState
    }
}

