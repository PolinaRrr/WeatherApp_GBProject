package com.example.weatherapp_gbproject

import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.dto.FactDTO
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.repository.getDefaultCity


class DataConverter {
    fun convertDtoToModel(weatherDTO: WeatherDTO): WeatherInfo {
        val factWeather: FactDTO = weatherDTO.fact
        return WeatherInfo(getDefaultCity(),
            factWeather.temp,
            factWeather.feelsLike,
            factWeather.condition,
            factWeather.windSpeed,
            factWeather.windDir,
            factWeather.pressureMm)
    }
}