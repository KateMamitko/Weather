package com.example.weather.presentation.search

import com.example.weather.domain.entety.City


sealed interface SearchEvents {

    data class ChangeSearchQuery(val query: String) : SearchEvents
    data object ClickBack : SearchEvents
    data object ClickSearchButton : SearchEvents
    data class ClickToItemCityList(val city: City,val openReason: OpenReason) : SearchEvents
}