package com.example.weather.domain.entety

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(val id: String, val cityName: String, val country: String) : Parcelable