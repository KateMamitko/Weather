package com.example.weather.presentation.search

import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow

interface SearchComponent {

    val model: StateFlow<SearchState>
    val openReason: OpenReason

    fun changeSearchQuery(query: String)
    fun onClickBack()
    fun onClickSearch()
    fun onClickToItemCity(city: City, openReason: OpenReason)
}