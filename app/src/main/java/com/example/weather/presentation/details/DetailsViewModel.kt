package com.example.weather.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.weather.domain.entety.City
import com.example.weather.domain.usecase.AddToFevListUseCase
import com.example.weather.domain.usecase.DeleteFromFavListUseCase
import com.example.weather.domain.usecase.GetForecastUseCase
import com.example.weather.domain.usecase.ObserveIsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addToFevListUseCase: AddToFevListUseCase,
    private val deleteFromFavListUseCase: DeleteFromFavListUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel(),
    ContainerHost<DetailsState, DetailsSideEffects>, InstanceKeeper.Instance {

    override val container: Container<DetailsState, DetailsSideEffects> = container(
        initialState = DetailsState(
            false, City("", "", ""),
            DetailsState.ForecastState.Initial
        )
    )

    private var isLoaded = false

    fun load(city: City) = intent {
        isLoaded = true

        reduce { state.copy(forecastState = DetailsState.ForecastState.Loading) }

        try {
            val forecast = getForecastUseCase(city)
            reduce {
                state.copy(
                    city = city,
                    forecastState = DetailsState.ForecastState.Complete(forecast)
                )
            }
            viewModelScope.launch {
                val isFav = observeIsFavoriteUseCase(city.id).first()
                reduce {
                    state.copy(isFavorite = isFav)
                }
            }
        } catch (e: Exception) {
            reduce { state.copy(forecastState = DetailsState.ForecastState.Error) }
            postSideEffect(DetailsSideEffects.ShowMessage(e.message ?: "Error"))
        }
    }

    fun onEvents(events: DetailsEvents) = intent {
        when (events) {
            is DetailsEvents.ChangeFavoriteStatus -> {
                val newStatus = !state.isFavorite
                if (newStatus) {
                    addToFevListUseCase(events.city)
                } else {
                    deleteFromFavListUseCase(events.city.id)
                }
                reduce { state.copy(isFavorite = newStatus) }
            }

            is DetailsEvents.ClickOnBack -> {
                postSideEffect(DetailsSideEffects.OnBack)
            }

            is DetailsEvents.Loading -> {
                try {
                    val forecast = getForecastUseCase(events.city)
                    reduce {
                        state.copy(
                            city = events.city,
                            forecastState = DetailsState.ForecastState.Complete(forecast)
                        )
                    }
                } catch (e: Exception) {
                    reduce {
                        state.copy(
                            forecastState = DetailsState.ForecastState.Error
                        )
                    }
                    postSideEffect(DetailsSideEffects.ShowMessage(e.message.toString()))
                }
            }
        }
    }
}