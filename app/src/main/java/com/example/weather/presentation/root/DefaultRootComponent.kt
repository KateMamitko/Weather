package com.example.weather.presentation.root


import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.weather.domain.entety.City
import com.example.weather.presentation.details.DefaultDetailsComponent
import com.example.weather.presentation.details.DetailsViewModel
import com.example.weather.presentation.favorite.DefaultFavoriteComponent
import com.example.weather.presentation.favorite.FavoriteViewModel
import com.example.weather.presentation.root.RootComponent.Child.Details
import com.example.weather.presentation.root.RootComponent.Child.Favorite
import com.example.weather.presentation.root.RootComponent.Child.Search
import com.example.weather.presentation.search.DefaultSearchComponent
import com.example.weather.presentation.search.OpenReason
import com.example.weather.presentation.search.SearchViewModel
import kotlinx.parcelize.Parcelize

class DefaultRootComponent(
    context: ComponentContext,
    val detailsViewModel: DetailsViewModel,
    val favoriteViewModel: FavoriteViewModel,
    val searchViewModel: SearchViewModel,
    val showToast: (String) -> Unit,
) : RootComponent,
    ComponentContext by context {

    val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favorite,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            Config.Favorite -> Favorite(
                DefaultFavoriteComponent(
                    componentContext = componentContext,
                    viewModel = favoriteViewModel,
                    addToFavoriteComponent = {
                        navigation.push(Config.Search(OpenReason.AddToFavorite))
                    },
                    openSearchComponent = {
                        navigation.push(Config.Search(OpenReason.RegularSearch))
                    },
                    openDetailsComponent = {
                        navigation.push(Config.Details(it))
                    },

                    showToast = { showToast(it) }
                )
            )

            is Config.Search -> Search(
                DefaultSearchComponent(
                    context = componentContext,
                    viewModel = searchViewModel,
                    onBack = {
                        navigation.pop()
                    },
                    openForecast = { navigation.push(Config.Details(it)) },
                    saveToFavorite = { navigation.pop() },
                    showErrorMessage = { showToast(it) },
                    reason = config.openReason
                )
            )

            is Config.Details -> {
                Details(
                    DefaultDetailsComponent(
                        city = config.city,
                        componentContext = componentContext,
                        viewModel = detailsViewModel,
                        clickToBack = {
                            navigation.pop()
                        },
                        showToast = { showToast(it) }
                    )
                )
            }
        }
    }

    @Parcelize
    sealed interface Config : Parcelable {
        @Parcelize
        data object Favorite : Config

        @Parcelize
        data class Details(val city: City) : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config
    }
}