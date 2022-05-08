package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.repository.dto.WeatherDTO

fun interface OnServerResponse {
    fun onResponse(weatherDTO: WeatherDTO)
}