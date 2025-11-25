package com.example.weather.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity ("city")
data class CityDbModel(
    @PrimaryKey val id: String,
    val cityName: String,
    val country: String
)
