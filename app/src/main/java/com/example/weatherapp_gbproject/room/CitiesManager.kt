package com.example.weatherapp_gbproject.room

import com.example.weatherapp_gbproject.WeatherApp
import com.example.weatherapp_gbproject.repository.dto.CoordinatesDTO


class CitiesManager {

    fun getCities(isRussian: Boolean): List<String> {
        return WeatherApp.getCitiesTable().getAllLocaleCities(isRussian)
    }
    fun getCoordinates(cityName:String):CoordinatesDTO{
        return WeatherApp.getCitiesTable().getCoordinatesCity(cityName)
    }
}