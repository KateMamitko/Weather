package com.example.weather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelTest @Inject constructor(val apiService: ApiService) : ViewModel() {

    fun load() {
        viewModelScope.launch {
            val str = apiService.loadCurrentWaether("London")
            println("str - $str")
        }
    }
}