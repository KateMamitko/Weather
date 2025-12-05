package com.example.weather.presentation.search

import com.example.weather.domain.entety.City

data class SearchState(
    val searchQuery: String,
    val searchResult: SearchResult
) {
    sealed interface SearchResult {
        data object Initial : SearchResult
        data object Loading : SearchResult
        data class Complete(val listCity: List<City>) : SearchResult
    }
}
