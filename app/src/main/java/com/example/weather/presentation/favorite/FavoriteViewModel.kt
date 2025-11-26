package com.example.weather.presentation.favorite

import androidx.lifecycle.ViewModel
import com.example.weather.domain.usecase.GetCurrentWeatherUseCase
import com.example.weather.domain.usecase.GetFavoriteCityListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCityListUseCase: GetFavoriteCityListUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel(), ContainerHost<FavoriteState, FavoriteSideEffect> {


    override val container =
        container<FavoriteState, FavoriteSideEffect>(
            initialState = FavoriteState(
                cityItem = FavoriteState.CityItem(
                    "",
                    FavoriteState.WeatherState.Initial
                )
            )
        )


    fun load() = intent {
        getFavoriteCityListUseCase().collect { listCities ->
            listCities.forEach {
                reduce {
                    state.copy(
                        cityItem = FavoriteState.CityItem(
                            cityName = it.cityName,
                            FavoriteState.WeatherState.Loading
                        )
                    )
                }
                onEvent(FavoriteEvent.StartLoading(it))
            }
        }
    }


    fun onEvent(event: FavoriteEvent) = intent {
        when (event) {

            is FavoriteEvent.ClickAddFavorite -> {
                postSideEffect(FavoriteSideEffect.ClickAddFavorite)
            }

            is FavoriteEvent.ClicksSearchCity -> {
                postSideEffect(FavoriteSideEffect.ClicksSearchCity)
            }

            is FavoriteEvent.OpenDetailsInfo -> {
                postSideEffect(FavoriteSideEffect.OpenDetailsInfo(event.city))
            }

            is FavoriteEvent.StartLoading -> {
                val name = event.city.cityName
                try {
                    val data = getCurrentWeatherUseCase(name)
                    reduce {
                        state.copy(
                            cityItem = FavoriteState.CityItem(
                                cityName = name,
                                weatherState = FavoriteState.WeatherState.Complete(
                                    temperature = data.temperature,
                                    icon = data.conditionUrl
                                )
                            )
                        )
                    }
                } catch (e: Exception) {
                    reduce {
                        state.copy(
                            cityItem = FavoriteState.CityItem(
                                cityName = name,
                                weatherState = FavoriteState.WeatherState.Error
                            )
                        )
                    }
                    postSideEffect(FavoriteSideEffect.ShowErrorMessage(e.message.toString()))
                }
            }

        }
    }
}
