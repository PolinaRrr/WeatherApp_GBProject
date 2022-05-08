package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel.Callback

interface DetailsWeatherRepository {
    fun getWeatherDetails(city: City,callback: Callback, errorCallback: DetailsViewModel.ErrorCallback)
}