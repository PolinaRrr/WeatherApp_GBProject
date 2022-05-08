package com.example.weatherapp_gbproject.repository

class LocalRepositoryImpl : LocalRepository {

    override fun getWorldWeatherLocalStorage() = getWorldCities()

    override fun getRussianWeatherLocalStorage() = getRussianCities()

    override fun getWeatherFromFromServer(): WeatherInfo {
        return WeatherInfo()
    }

}