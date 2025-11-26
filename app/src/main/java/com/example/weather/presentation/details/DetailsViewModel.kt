package com.example.weather.presentation.details

import androidx.lifecycle.ViewModel
import com.example.weather.domain.entety.City
import com.example.weather.domain.usecase.AddToFevListUseCase
import com.example.weather.domain.usecase.DeleteFromFavListUseCase
import com.example.weather.domain.usecase.GetForecastUseCase
import com.example.weather.domain.usecase.ObserveIsFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    ContainerHost<DetailsState, DetailsSideEffects> {

    override val container: Container<DetailsState, DetailsSideEffects>
        get() = container(
            initialState = DetailsState(
                false, "",
                DetailsState.ForecastState.Initial
            )
        )

    fun load(city: City) = intent {
        reduce {
            state.copy(forecastState = DetailsState.ForecastState.Loading)
        }
        onEvents(DetailsEvents.Loading(city))
    }

    fun onEvents(events: DetailsEvents) = intent {
        when (events) {
            is DetailsEvents.ChangeFavoriteStatus -> {
                if (state.isFavorite) {
                    addToFevListUseCase(events.city)
                } else {
                    deleteFromFavListUseCase(events.city.id)
                }
                observeIsFavoriteUseCase(events.city.id).collect {
                    reduce {
                        state.copy(isFavorite = it)
                    }
                }
            }

            is DetailsEvents.ClickOnBack -> {
                postSideEffect(DetailsSideEffects.OnBack)
            }

            is DetailsEvents.Loading -> {
                try {
                    val forecast = getForecastUseCase(events.city.cityName)
                    reduce {
                        state.copy(
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