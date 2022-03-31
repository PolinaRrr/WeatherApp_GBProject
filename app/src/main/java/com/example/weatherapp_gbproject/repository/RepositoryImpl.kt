package com.example.weatherapp_gbproject.repository

class RepositoryImpl:Repository {
    override fun getWeatherFromFromServer(): WeatherInfo {
        return WeatherInfo()
    }

    override fun getWorldWeatherLocalStorage():List<WeatherInfo> {
        return getWorldCities()
    }
    override fun getRussianWeatherLocalStorage():List<WeatherInfo> {
        return getRussianCities()
    }
}