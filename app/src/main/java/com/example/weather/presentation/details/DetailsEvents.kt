package com.example.weather.presentation.details

import com.example.weather.domain.entety.City


sealed interface DetailsEvents {

    data object ClickOnBack : DetailsEvents
    data class Loading(val city: City) : DetailsEvents
    data class ChangeFavoriteStatus(val city: City) : DetailsEvents
}