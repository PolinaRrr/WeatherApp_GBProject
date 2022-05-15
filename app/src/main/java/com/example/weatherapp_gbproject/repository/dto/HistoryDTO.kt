package com.example.weatherapp_gbproject.repository.dto

data class HistoryDTO(
    val city_name: String,
    val temperature: Int,
    val feels_like: Int,
    val condition: String,
    val icon: String,
    val wind_speed: Double,
    val wind_dir: String,
    val pressure_mm: Int
)
