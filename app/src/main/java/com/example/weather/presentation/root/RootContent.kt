package com.example.weather.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.weather.presentation.details.DetailsContent
import com.example.weather.presentation.favorite.FavoriteContent
import com.example.weather.presentation.search.SearchContent
import com.example.weather.presentation.ui.theme.WeatherTheme

@Composable
fun RootContent(rootComponent: RootComponent) {
    WeatherTheme {
        Children(rootComponent.stack) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> DetailsContent(instance.component)
                is RootComponent.Child.Favorite -> FavoriteContent(instance.component)
                is RootComponent.Child.Search -> SearchContent(instance.component)
            }
        }
    }

}