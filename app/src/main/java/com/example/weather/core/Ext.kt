package com.example.weather.core

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

fun ComponentContext.componentScope() =
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()).apply {
        doOnDestroy {
            cancel()
        }
    }


fun Float.formattingTemperature() = "${this.roundToInt()} °C"

fun Calendar.getFullDate(): String {
    val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(this.time)
    val data = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(time)
    return "$dayOfWeek | $data"
}

fun Calendar.getdayOfTheWeek() = SimpleDateFormat("EE", Locale.getDefault()).format(time)