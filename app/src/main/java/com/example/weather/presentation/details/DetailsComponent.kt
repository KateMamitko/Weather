package com.example.weather.presentation.details

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {

    val model: StateFlow<DetailsState>

    fun goToBack()
    fun changeFavoriteStatus(city: City)

}