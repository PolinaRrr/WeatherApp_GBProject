package com.example.weatherapp_gbproject.room

import com.example.weatherapp_gbproject.repository.dto.HistoryDTO
import com.example.weatherapp_gbproject.WeatherApp

class HistoryManager {

    val lastHistory: List<HistoryDTO> = getLastHistoryFromTables()

    private fun getLastHistoryFromTables(): List<HistoryDTO> {
        return WeatherApp.getHistoryWeatherTable().getHistory()
    }

    fun putEntryToHistoryTable(history: HistoryDTO) {
        Thread{
            WeatherApp.getHistoryWeatherTable().insert(
                WeatherApp.getCitiesTable().getIdCity(history.city_name),
                history.temperature,
                history.feels_like,
                history.condition,
                history.icon,
                history.wind_speed,
                history.wind_dir,
                history.pressure_mm
            )
        }.start()
    }
}