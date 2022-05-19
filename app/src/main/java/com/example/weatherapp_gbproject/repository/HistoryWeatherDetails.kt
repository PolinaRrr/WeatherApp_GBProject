package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel

interface HistoryWeatherDetails {
    fun getHistoryWeatherDetails(callback: HistoryViewModel.CallbackFullInfo)
}