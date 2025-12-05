package com.example.weather.presentation.details

import com.example.weather.domain.entety.City
import com.example.weather.domain.entety.Forecast

data class DetailsState(
    var isFavorite: Boolean,
    val city: City,
    val forecastState: ForecastState
) {

    sealed interface ForecastState {
        data object Initial : ForecastState
        data object Loading : ForecastState

        data class Complete(
            val forecast: Forecast
        ) : ForecastState

        data object Error : ForecastState
    }
}
