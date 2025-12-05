package com.example.weather.data.network.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
   @SerializedName ("name") val name: String,
   @SerializedName ("country") val country: String,
   @SerializedName ("region") val id: String,
)