package com.example.weather.presentation.search

import androidx.lifecycle.ViewModel
import com.example.weather.domain.usecase.SearchCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel(),
    ContainerHost<SearchState, SearchSideEffects> {
    override val container: Container<SearchState, SearchSideEffects>
        get() = container(initialState = SearchState("", SearchState.SearchResult.Initial))


    fun onEvent(events: SearchEvents) = intent {
        when (events) {
            is SearchEvents.ChangeSearchQuery -> {
                reduce {
                    state.copy(searchQuery = events.query)
                }
            }

            is SearchEvents.ClickBack -> {
                postSideEffect(SearchSideEffects.OnBack)
            }

            is SearchEvents.ClickSearchButton -> {
                reduce {
                    state.copy(searchResult = SearchState.SearchResult.Loading)
                }
                try {
                    val res = searchCityUseCase(state.searchQuery)
                    reduce {
                        state.copy(searchResult = SearchState.SearchResult.Complete(res))
                    }
                } catch (e: Exception) {
                    postSideEffect(SearchSideEffects.ShowErrorMessage(e.message.toString()))
                }
            }

            is SearchEvents.ClickToItemCityList -> {
                if (events.openReason == OpenReason.AddToFavorite) {
                    postSideEffect(SearchSideEffects.SaveToFavorite)
                } else {
                    postSideEffect(SearchSideEffects.OpenForecast(events.city))
                }
            }
        }
    }
}