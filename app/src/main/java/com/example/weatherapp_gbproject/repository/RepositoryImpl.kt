package com.example.weatherapp_gbproject.repository

class RepositoryImpl:Repository {
    override fun getWeatherFromLocalStorage(): WeatherInfo {

        return WeatherInfo()
    }

    override fun getWeatherFromServer():WeatherInfo {
        Thread.sleep(3000L)
        return WeatherInfo()
    }
}