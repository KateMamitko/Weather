package com.example.weather.presentation.search

import com.example.weather.domain.entety.City


sealed interface SearchSideEffects {

    data object OnBack : SearchSideEffects
    data object SaveToFavorite : SearchSideEffects
    data class OpenForecast(val city: City) : SearchSideEffects
    data class ShowErrorMessage(val errorMessage: String) : SearchSideEffects

}