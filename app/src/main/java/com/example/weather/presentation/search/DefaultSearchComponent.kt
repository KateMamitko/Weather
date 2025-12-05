package com.example.weather.presentation.search

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.example.weather.core.componentScope
import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultSearchComponent(
    val context: ComponentContext,
    val viewModel: SearchViewModel,
    val onBack: () -> Unit,
    val openForecast: (City) -> Unit,
    val saveToFavorite: () -> Unit,
    val showErrorMessage: (String) -> Unit,
    val reason: OpenReason
) : SearchComponent,
    ComponentContext by context {
    val state = instanceKeeper.getOrCreate { viewModel }

    init {
        componentScope().launch {
            state.container.sideEffectFlow.collect {
                when (it) {
                    is SearchSideEffects.OnBack -> onBack()
                    is SearchSideEffects.OpenForecast -> openForecast(it.city)
                    is SearchSideEffects.SaveToFavorite -> saveToFavorite()
                    is SearchSideEffects.ShowErrorMessage -> showErrorMessage(it.errorMessage)
                }
            }
        }
    }

    override val model: StateFlow<SearchState>
        get() = state.container.stateFlow

    override val openReason: OpenReason
        get() = reason

    override fun changeSearchQuery(query: String) {
        viewModel.onEvent(SearchEvents.ChangeSearchQuery(query))
    }

    override fun onClickBack() {
        viewModel.onEvent(SearchEvents.ClickBack)
    }

    override fun onClickSearch() {
        viewModel.onEvent(SearchEvents.ClickSearchButton)
    }

    override fun onClickToItemCity(
        city: City,
        openReason: OpenReason
    ) {
        viewModel.onEvent(SearchEvents.ClickToItemCityList(city, openReason))
    }

}