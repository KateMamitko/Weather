package com.example.weather.presentation.favorite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.example.weather.core.componentScope
import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultFavoriteComponent(
    val componentContext: ComponentContext,
    private val viewModel: FavoriteViewModel,
    private val addToFavoriteComponent: () -> Unit,
    private val openSearchComponent: () -> Unit,
    private val openDetailsComponent: (City) -> Unit,
    private val showToast: (String) -> Unit,
) : FavoriteComponent,
    ComponentContext by componentContext {

    private val vm = instanceKeeper.getOrCreate { viewModel }

    init {
        componentScope().launch {
            vm.container.sideEffectFlow.collect {
                when (it) {
                    is FavoriteSideEffect.ClickAddFavorite -> addToFavoriteComponent()
                    is FavoriteSideEffect.ClicksSearchCity -> openSearchComponent()
                    is FavoriteSideEffect.OpenDetailsInfo -> {
                        openDetailsComponent(it.city)
                    }

                    is FavoriteSideEffect.ShowErrorMessage -> showToast(it.str)
                }
            }
        }
    }

    override val model: StateFlow<FavoriteState> = vm.container.stateFlow

    override fun onClickSearch() {
        viewModel.onEvent(FavoriteEvent.ClicksSearchCity)
    }

    override fun onClickAddFavorite() {
        viewModel.onEvent(FavoriteEvent.ClickAddFavorite)
    }

    override fun clickToItemCity(city: City) {
        viewModel.onEvent(FavoriteEvent.OpenDetailsInfo(city))
    }

    override fun getLocation() {
        viewModel.getLocationAsync()
    }

    override fun load() {
        viewModel.load()
    }

}