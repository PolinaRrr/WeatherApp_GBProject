package com.example.weatherapp_gbproject.repository
import com.example.weatherapp_gbproject.repository.dto.CitiesDTO

interface LocalRepository {
    fun getWorldWeatherLocalStorage(): List<WeatherInfo>
    fun getRussianWeatherLocalStorage(): List<WeatherInfo>
    fun getWeatherFromFromServer(): WeatherInfo
}