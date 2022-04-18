package com.example.weatherapp_gbproject.repository

fun interface OnServerResponse {
    fun onResponce(weatherDTO: WeatherDTO)
}