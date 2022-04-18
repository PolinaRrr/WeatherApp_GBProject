package com.example.weatherapp_gbproject.repository


import com.google.gson.annotations.SerializedName

data class FactDTO(
    @SerializedName("condition")
    val condition: String,
    @SerializedName("temp")
    val temp: Int,
    @SerializedName("feels_like")
    val feelsLike: Int,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
)