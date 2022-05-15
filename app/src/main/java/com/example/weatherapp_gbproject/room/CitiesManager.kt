package com.example.weatherapp_gbproject.room

import com.example.weatherapp_gbproject.WeatherApp

class CitiesManager {
    fun getCities(isRussian: Boolean): List<String> {
        return WeatherApp.getCitiesTable().getAllLocaleCities(isRussian)
    }
}