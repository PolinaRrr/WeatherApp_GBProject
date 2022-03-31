package com.example.weatherapp_gbproject.repository

interface Repository {
    fun getWorldWeatherLocalStorage(): List<WeatherInfo>
    fun getRussianWeatherLocalStorage(): List<WeatherInfo>
    fun getWeatherFromFromServer():WeatherInfo
}