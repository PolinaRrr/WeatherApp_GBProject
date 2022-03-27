package com.example.weatherapp_gbproject.viewmodel

import com.example.weatherapp_gbproject.repository.WeatherInfo

sealed class AppState {
    object Loading: AppState()
    data class Success(val weatherInfo: WeatherInfo): AppState()
    data class Error( val error:Throwable):AppState()
}