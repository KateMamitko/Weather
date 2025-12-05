package com.example.weather.presentation.details


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.weather.core.formattingTemperature
import com.example.weather.core.getFullDate
import com.example.weather.core.getdayOfTheWeek
import com.example.weather.domain.entety.Weather
import com.example.weather.presentation.ui.CardGradients.themeGradient
import com.example.weather.presentation.ui.theme.Orange


@Composable
fun DetailsContent(detailsComponent: DetailsComponent) {
    val model by detailsComponent.model.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    themeGradient
                )
            )
    ) {
        Topbar(
            modifier = Modifier
                .align(alignment = Alignment.TopCenter)
                .padding(10.dp),
            model.city.cityName, model.isFavorite,
            changeFavorite = {
                detailsComponent.changeFavoriteStatus(model.city)
            },
            onBackPressed = {
                detailsComponent.goToBack()
            }
        )
        when (val state = model.forecastState) {
            is DetailsState.ForecastState.Complete -> {
                CurrentWeather(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .padding(bottom = 200.dp),
                    state.forecast.currentWeather
                )
                ForecastCard(
                    modifier = Modifier.align(alignment = Alignment.BottomCenter),
                    state.forecast.upcoming
                )
            }

            DetailsState.ForecastState.Error -> {
                Text(text = "Opps, try it late...")
            }

            DetailsState.ForecastState.Initial -> {}
            DetailsState.ForecastState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(75.dp),
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}

@Composable
fun Topbar(
    modifier: Modifier = Modifier,
    cityName: String,
    isfav: Boolean,
    changeFavorite: () -> Unit,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "on back icon",
            Modifier
                .size(30.dp)
                .clickable {
                    onBackPressed()
                }
        )
        Text(
            text = cityName,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black,
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)

        )
        Icon(
            imageVector = Icons.Default.Star, contentDescription = "is favorite",
            tint = if (isfav) {
                Orange
            } else {
                Color.Gray
            },
            modifier = Modifier
                .padding(end = 10.dp)
                .clickable {
                    changeFavorite()
                }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurrentWeather(modifier: Modifier, currentWeather: Weather) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GlideImage(model = currentWeather.conditionUrl, "weather icon", Modifier.size(120.dp))
        Text(
            text = currentWeather.description,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 28.sp)
        )
        Text(
            text = currentWeather.temperature.formattingTemperature(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 34.sp)
        )

        Text(text = currentWeather.date.getFullDate())
    }
}

@Composable
fun ForecastCard(
    modifier: Modifier, upcoming: List<Weather>
) {
    val shape = MaterialTheme.shapes.extraLarge
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 50.dp)
            .background(Color.Transparent, shape = shape)
            .border(1.dp, MaterialTheme.colorScheme.outline, shape),
        shape = shape
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Upcoming",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surface,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                upcoming.take(3).forEach { weather ->
                    val dayOfWeek = weather.date.getdayOfTheWeek()
                    ForecastItemCard(
                        temp = weather.temperature,
                        icon = weather.conditionUrl,
                        dayOfWeek = dayOfWeek
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ForecastItemCard(temp: Float, icon: String, dayOfWeek: String) {
    Card(
        modifier = Modifier
            .width(90.dp)
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = temp.formattingTemperature(),
                modifier = Modifier
                    .padding(top = 5.dp),
                color = MaterialTheme.colorScheme.surface
            )
            GlideImage(
                icon, null, Modifier
                    .size(40.dp)
            )
            Text(
                text = dayOfWeek,
                modifier = Modifier
                    .padding(bottom = 5.dp),
                color = MaterialTheme.colorScheme.surface
            )
        }

    }
}