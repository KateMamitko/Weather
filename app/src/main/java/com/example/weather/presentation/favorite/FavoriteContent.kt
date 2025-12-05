package com.example.weather.presentation.favorite

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weather.R
import com.example.weather.core.formattingTemperature
import com.example.weather.domain.entety.City
import com.example.weather.presentation.ui.CardGradients
import com.example.weather.presentation.ui.Gradient
import com.example.weather.presentation.ui.theme.Orange
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FavoriteContent(favoriteComponent: FavoriteComponent) {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )
    LaunchedEffect(Unit) {
        favoriteComponent.load()
    }

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            favoriteComponent.getLocation()
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    val state by favoriteComponent.model.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar {
            favoriteComponent.onClickSearch()
        }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = state.cityItem,
                key = { index, item -> item.city }
            ) { index, item ->
                ItemCard(item, index) { city ->
                    favoriteComponent.clickToItemCity(city)
                }
            }

            item {
                ItemCardAddToFav {
                    favoriteComponent.onClickAddFavorite()
                }
            }
        }
    }


}

@Composable
fun SearchBar(onClickSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Color(0xFF70C7F3), Color(0xFF045698))),
                shape = CircleShape
            )
            .clickable {
                onClickSearch()
            },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Default.Search,
            modifier = Modifier.padding(start = 30.dp),
            contentDescription = stringResource(R.string.search_icon),
            tint = MaterialTheme.colorScheme.background
        )
        Text(
            text = stringResource(R.string.search),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
        )
    }
}

@Composable
fun ItemCardAddToFav(onClickToItem: () -> Unit) {
    val shape = MaterialTheme.shapes.extraLarge
    Card(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .shadow(15.dp, shape = shape, spotColor = Color.Black)
            .border(2.dp, color = Color.Black, shape)
            .clickable {
                onClickToItem()
            },
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Edit, "add to fav",
                Modifier
                    .size(50.dp)
                    .align(alignment = Alignment.Center)
                    .padding(bottom = 8.dp),
                tint = Orange
            )
            Spacer(Modifier.height(30.dp))
            Text(
                "Add favorite",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
            )
        }

    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemCard(
    cityItem: FavoriteState.CityItem,
    index: Int, onClickToItem: (City) -> Unit
) {
    val data = getGradientByIndex(index)
    val shape = MaterialTheme.shapes.extraLarge
    Card(
        modifier = Modifier
            .fillMaxSize()
            .shadow(15.dp, shape = shape, spotColor = Color.Black)
            .clickable {
                onClickToItem(cityItem.city)
            },
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(data.mainColor)
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = data.secondColor,
                        radius = size.maxDimension / 2,
                        center = Offset(
                            center.x - size.width / 10,
                            center.y + size.height / 2
                        )
                    )
                },

            contentAlignment = Alignment.Center,
        ) {
            when (val state = cityItem.weatherState) {
                is FavoriteState.WeatherState.Complete -> {
                    GlideImage(
                        state.icon, contentDescription = "weather icon",
                        modifier = Modifier
                            .align(alignment = Alignment.TopEnd)
                            .padding(end = 8.dp)
                            .size(86.dp)
                    )

                    Text(
                        text = state.temperature.formattingTemperature(),
                        modifier = Modifier
                            .align(alignment = Alignment.BottomStart)
                            .padding(start = 20.dp, bottom = 35.dp),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp)
                    )
                }

                is FavoriteState.WeatherState.Error -> {
                    ErrorText()
                }

                is FavoriteState.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier =
                            Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }

            Text(
                text = cityItem.city.cityName,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 25.dp,
                        bottom = 15.dp
                    ),
                color = MaterialTheme.colorScheme.background
            )
        }

    }
}


@Composable
fun ErrorText() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Oops, try again",
            color = MaterialTheme.colorScheme.background
        )
    }
}

fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradients.gradients
    return gradients[index % gradients.size]
}
