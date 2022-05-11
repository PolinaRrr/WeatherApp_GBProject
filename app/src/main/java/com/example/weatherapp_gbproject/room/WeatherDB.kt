package com.example.weatherapp_gbproject.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cities::class, HistoryWeatherRequest::class], version = 1)
abstract class WeatherDB : RoomDatabase() {
    abstract fun getHistoryWeather(): HistoryWeatherDao
    abstract fun getCities(): CitiesDao
}