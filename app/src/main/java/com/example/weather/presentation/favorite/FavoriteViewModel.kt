package com.example.weather.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.example.weather.domain.entety.City
import com.example.weather.domain.usecase.AddToFevListUseCase
import com.example.weather.domain.usecase.GetCurrentWeatherUseCase
import com.example.weather.domain.usecase.GetFavoriteCityListUseCase
import com.example.weather.domain.usecase.GetLocationUseCase
import com.example.weather.domain.usecase.GetWeatherByCoordinatesUseCase
import com.example.weather.presentation.favorite.FavoriteSideEffect.ClickAddFavorite
import com.example.weather.presentation.favorite.FavoriteSideEffect.ClicksSearchCity
import com.example.weather.presentation.favorite.FavoriteSideEffect.OpenDetailsInfo
import com.example.weather.presentation.favorite.FavoriteState.WeatherState.Complete
import com.example.weather.presentation.favorite.FavoriteState.WeatherState.Error
import com.example.weather.presentation.favorite.FavoriteState.WeatherState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteCityListUseCase: GetFavoriteCityListUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val getWeatherByCoordinatesUseCase: GetWeatherByCoordinatesUseCase,
    private val addToFevListUseCase: AddToFevListUseCase
) : ViewModel(), ContainerHost<FavoriteState, FavoriteSideEffect>, InstanceKeeper.Instance {


    override val container =
        container<FavoriteState, FavoriteSideEffect>(
            initialState = FavoriteState(
                cityItem = emptyList()
            )
        )


    fun load() = intent {
        getFavoriteCityListUseCase().collect { listCities ->
            reduce {
                state.copy(
                    cityItem = listCities.map {
                        FavoriteState.CityItem(
                            city = it,
                            weatherState = Loading
                        )
                    }
                )
            }

            listCities.forEach {
                onEvent(FavoriteEvent.StartLoading(it))
            }
        }
    }


    fun onEvent(event: FavoriteEvent) = intent {
        when (event) {

            is FavoriteEvent.ClickAddFavorite -> {
                postSideEffect(ClickAddFavorite)
            }

            is FavoriteEvent.ClicksSearchCity -> {
                postSideEffect(ClicksSearchCity)
            }

            is FavoriteEvent.OpenDetailsInfo -> {
                postSideEffect(OpenDetailsInfo(event.city))
            }

            is FavoriteEvent.StartLoading -> {
                val city = event.city
                try {
                    val data = getCurrentWeatherUseCase(city.cityName)

                    reduce {
                        val updatedList = state.cityItem.map {
                            if (it.city.cityName == city.cityName) {
                                it.copy(
                                    weatherState = Complete(
                                        temperature = data.temperature,
                                        icon = data.conditionUrl
                                    )
                                )
                            } else it
                        }

                        state.copy(cityItem = updatedList)
                    }
                } catch (e: Exception) {
                    reduce {
                        val updatedList = state.cityItem.map {
                            if (it.city.cityName == city.cityName) {
                                it.copy(
                                    weatherState = Error
                                )
                            } else it
                        }

                        state.copy(cityItem = updatedList)
                    }

                }
            }

            FavoriteEvent.GetLocation -> {
                reduce {
                    state.copy(
                        cityItem = state.cityItem + FavoriteState.CityItem(
                            city = City(
                                id = "-1",
                                cityName = "Detecting...",
                                country = ""
                            ),
                            weatherState = Loading
                        )
                    )
                }
                viewModelScope.launch {
                    val location = getLocationUseCase()

                    if (location != null) {
                        val weather =
                            getWeatherByCoordinatesUseCase(location.latitude, location.longitude)
                        addToFevListUseCase(
                            City(
                                weather.cityId,
                                cityName = weather.cityName,
                                country = weather.country
                            )
                        )

                        intent {
                            reduce {
                                state.copy(
                                    cityItem = state.cityItem.map {
                                        if (it.city.id == "-1") {
                                            it.copy(
                                                city = City(
                                                    id = weather.cityId,
                                                    weather.cityName,
                                                    weather.country
                                                ),
                                                weatherState = Complete(
                                                    temperature = weather.temperature,
                                                    icon = weather.conditionUrl
                                                )
                                            )
                                        } else it
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun getLocationAsync() {
        viewModelScope.launch {
            onEvent(FavoriteEvent.GetLocation)
        }
    }
}
