package com.example.weather.data.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.example.weather.data.mappers.toDomain
import com.example.weather.data.network.ApiService
import com.example.weather.domain.entety.MyLocationWeather
import com.example.weather.domain.repository.LocationRepository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    val apiService: ApiService
) : LocationRepository {
    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = suspendCoroutine { continuation ->
        fusedClient.lastLocation
            .addOnSuccessListener { location ->
                println("Success-${location}")
                if (location != null) {
                    continuation.resume(location)
                } else {
                    requestNewLocation(continuation)
                }
            }
            .addOnFailureListener {
                println("err-${it.message}")
                continuation.resume(null)
            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation(continuation: Continuation<Location?>) {
        val request = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            1000L
        ).setMaxUpdates(1).build()

        fusedClient.requestLocationUpdates(
            request,
            object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fusedClient.removeLocationUpdates(this)
                    println("result.lastLocation - ${result.lastLocation}")
                    continuation.resume(result.lastLocation)
                }
            },
            Looper.getMainLooper()
        )
    }

    override suspend fun getWeatherByCoordinates(
        a: Double,
        b: Double
    ): MyLocationWeather {
        val data = apiService.getMyLocationWeather("$a,$b")
        return data.toDomain()
    }
}