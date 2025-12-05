package com.example.weather.presentation.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.example.weather.presentation.details.DetailsContent
import com.example.weather.presentation.favorite.FavoriteContent
import com.example.weather.presentation.search.SearchContent
import com.example.weather.presentation.ui.theme.WeatherTheme

@Composable
fun RootContent(rootComponent: RootComponent) {
    WeatherTheme {
        Children(
            rootComponent.stack,
            modifier = Modifier
                .background(
                    Brush
                        .verticalGradient(
                            colors = listOf(
                                Color(0xFF70C7F3),
                                Color(0xFF285EBD)
                            )
                        )
                )
                .statusBarsPadding()
        ) {
            when (val instance = it.instance) {
                is RootComponent.Child.Details -> DetailsContent(instance.component)
                is RootComponent.Child.Favorite -> FavoriteContent(instance.component)
                is RootComponent.Child.Search -> SearchContent(instance.component)
            }
        }
    }
}