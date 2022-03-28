package com.example.weatherapp_gbproject.repository

data class WeatherInfo(
    val city: City = getDefaultCity(),
    val temp: Int = 0,
    val feels_like: Int = 0,
    val condition: String = "Cloudy",
    val wind_speed: Int = 2,
    val wind_dir: String = "northwest",
    val pressure_mm: Int = 759
)

fun getDefaultCity() = City("Saint-Petersburg", 59.938951, 30.315635)

data class City(
    val locality: String,
    val lat: Double,
    val lon: Double

)