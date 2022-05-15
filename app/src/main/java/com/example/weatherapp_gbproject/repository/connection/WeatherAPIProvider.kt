package com.example.weatherapp_gbproject.repository.connection

import com.example.weatherapp_gbproject.repository.KEY_WEATHER_LOADER_YANDEX_QUERY
import com.example.weatherapp_gbproject.repository.YANDEX_TARIFF_VERSION
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface WeatherAPIProvider {
    @GET(YANDEX_TARIFF_VERSION)
    fun getWeatherData(
        @Header(KEY_WEATHER_LOADER_YANDEX_QUERY) token: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

}