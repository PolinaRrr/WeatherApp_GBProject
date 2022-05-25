package com.example.weatherapp_gbproject.repository

interface WeatherRepositoryAdd : DetailsWeatherRepository {
    fun addWeather(weatherInfo: WeatherInfo)
}