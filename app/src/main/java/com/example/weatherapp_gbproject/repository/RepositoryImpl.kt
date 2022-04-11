package com.example.weatherapp_gbproject.repository

class RepositoryImpl : Repository {
    override fun getWeatherFromFromServer() = WeatherInfo()

    override fun getWorldWeatherLocalStorage() = getWorldCities()

    override fun getRussianWeatherLocalStorage() = getRussianCities()

}