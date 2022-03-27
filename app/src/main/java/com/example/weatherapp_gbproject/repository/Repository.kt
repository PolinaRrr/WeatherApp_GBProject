package com.example.weatherapp_gbproject.repository

interface Repository {
    fun getWeatherFromLocalStorage(): WeatherInfo
    fun getWeatherFromServer():WeatherInfo
}