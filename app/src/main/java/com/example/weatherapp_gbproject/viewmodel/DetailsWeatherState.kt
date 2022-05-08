package com.example.weatherapp_gbproject.viewmodel

import com.example.weatherapp_gbproject.repository.WeatherInfo

sealed class DetailsWeatherState {
    object Loading : DetailsWeatherState()
    data class Success(val weather: WeatherInfo) : DetailsWeatherState()
    data class Error(val state: ResponseState) : DetailsWeatherState()
}