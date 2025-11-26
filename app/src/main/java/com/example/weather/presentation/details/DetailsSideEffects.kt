package com.example.weather.presentation.details

sealed interface DetailsSideEffects {
    data object OnBack : DetailsSideEffects
    data class ShowMessage(val s: String) : DetailsSideEffects
}