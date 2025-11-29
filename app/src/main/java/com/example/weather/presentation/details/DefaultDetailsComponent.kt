package com.example.weather.presentation.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.example.weather.core.componentScope
import com.example.weather.domain.entety.City
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultDetailsComponent (
    val city: City,
    val componentContext: ComponentContext,
    val viewModel: DetailsViewModel,
    val clickToBack: () -> Unit,
    val showToast: (String) -> Unit,
) : DetailsComponent,
    ComponentContext by componentContext {

    val state = instanceKeeper.getOrCreate { viewModel }

    init {
        componentScope().launch {
            state.container.sideEffectFlow.collect {
                when (it) {
                    is DetailsSideEffects.OnBack -> clickToBack()
                    is DetailsSideEffects.ShowMessage -> showToast(it.s)
                }
            }
        }
    }

    override val model: StateFlow<DetailsState>
        get() = state.container.stateFlow

    override fun goToBack() {
        viewModel.onEvents(DetailsEvents.ClickOnBack)
    }

    override fun changeFavoriteStatus(city: City) {
        viewModel.onEvents(DetailsEvents.ChangeFavoriteStatus(city))
    }

}