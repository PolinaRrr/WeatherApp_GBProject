package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel

interface WeatherRepository {

    fun getWeather(
        callback: HistoryViewModel.CallbackFullInfo
    )
}