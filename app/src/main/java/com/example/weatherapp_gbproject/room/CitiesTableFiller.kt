package com.example.weatherapp_gbproject.room

import com.example.weatherapp_gbproject.WeatherApp
import com.example.weatherapp_gbproject.repository.dto.CitiesDTO
import com.example.weatherapp_gbproject.repository.dto.getListCities

class CitiesTableFiller {
    companion object {
        private val listCities: List<CitiesDTO> = getListCities()
        fun fillTablesCities() {
            for (i in listCities.indices) {
                WeatherApp.getCitiesTable().insert(
                    listCities[i].locality, listCities[i].lat,
                    listCities[i].lon, listCities[i].isRussian
                )
            }
        }
    }
}