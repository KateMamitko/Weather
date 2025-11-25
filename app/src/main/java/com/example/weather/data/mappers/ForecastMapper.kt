package com.example.weather.data.mappers


import com.example.weather.data.network.dto.CurrentWeatherDto
import com.example.weather.data.network.dto.ForecastDto
import com.example.weather.data.network.dto.WeatherCurrentDto
import com.example.weather.data.network.dto.WeatherDto
import com.example.weather.domain.entety.Forecast
import com.example.weather.domain.entety.Weather
import java.util.Calendar

fun CurrentWeatherDto.toDomain() = Forecast(
    currentWeather = this.currentWeather.toDomain(),
    upcoming = this.forecastList.listWeather.drop(1)
        .toDomain() // 1 день прогнозів буде в currentWeather
)

fun WeatherCurrentDto.toWeather() = Weather(
    temperature = this.currentWeather.temperature,
    description = this.currentWeather.condition.text,
    conditionUrl = this.currentWeather.condition.icon.buildUrl(),
    isFavorite = false,
    date = this.currentWeather.date.createCalendar()
)

fun WeatherDto.toDomain() = Weather(
    temperature = this.temperature,
    description = this.condition.text,
    conditionUrl = this.condition.icon.buildUrl(),
    isFavorite = false,
    date = this.date.createCalendar()
)

fun List<ForecastDto>.toDomain(): List<Weather> =
    map { item ->
        Weather(
            temperature = item.dayDto.temperature,
            description = item.dayDto.condition.text,
            conditionUrl = item.dayDto.condition.icon.buildUrl(),
            isFavorite = false,
            date = item.date_epoch.createCalendar()
        )
    }

const val START_ICON_URL = "https:"

private fun String.buildUrl() = START_ICON_URL + this.replace("64x64", "128x128")

private fun Long.createCalendar(): Calendar =
    Calendar.getInstance().apply {
        timeInMillis = this@createCalendar * 1000
    }