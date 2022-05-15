package com.example.weatherapp_gbproject

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.dto.FactDTO
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.repository.dto.HistoryDTO
import com.example.weatherapp_gbproject.repository.getDefaultCity
import com.example.weatherapp_gbproject.room.HistoryWeatherRequest
import com.example.weatherapp_gbproject.room.HistoryManager
import com.example.weatherapp_gbproject.room.CitiesManager
import com.example.weatherapp_gbproject.repository.City

class DataConverter {
    fun convertDtoToModel(weatherDTO: WeatherDTO): WeatherInfo {
        val factWeather: FactDTO = weatherDTO.fact
        return WeatherInfo(
            getDefaultCity(),
            factWeather.temp,
            factWeather.feelsLike,
            factWeather.condition,
            factWeather.icon,
            factWeather.windSpeed,
            factWeather.windDir,
            factWeather.pressureMm
        )
    }


    fun convertHistoryTableToWeather(historyTable: List<HistoryWeatherRequest>): List<WeatherInfo> {
        return historyTable.map {
            WeatherInfo(
                City(HistoryManager().lastHistory[0].city_name,
                    CitiesManager().getCoordinates(HistoryManager().lastHistory[0].city_name).lat,
                    CitiesManager().getCoordinates(HistoryManager().lastHistory[0].city_name).lon),
                it.temperature,
                it.feels_like,
                it.condition,
                it.icon,
                it.wind_speed,
                it.wind_dir,
                it.pressure_mm
            )
        }
    }
    //new

    fun convertHistoryToWeather(historyTable: List<HistoryDTO>): List<WeatherInfo> {
        return historyTable.map {
            WeatherInfo(
                City(
                    HistoryManager().lastHistory[0].city_name,
                    CitiesManager().getCoordinates(HistoryManager().lastHistory[0].city_name).lat,
                    CitiesManager().getCoordinates(HistoryManager().lastHistory[0].city_name).lon
                ),
                it.temperature,
                it.feels_like,
                it.condition,
                it.icon,
                it.wind_speed,
                it.wind_dir,
                it.pressure_mm
            )
        }
    }

    //new
    fun convertWeatherToHistory(weather: WeatherInfo): HistoryDTO {
        return HistoryDTO(
            weather.city.locality,
            weather.temperature,
            weather.feels_like,
            weather.condition,
            weather.icon,
            weather.wind_speed,
            weather.wind_dir,
            weather.pressure_mm
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertWeatherToHistoryTable(weather: WeatherInfo): HistoryWeatherRequest {
        return HistoryWeatherRequest(
            0,
            getCurrentTime(),
            WeatherApp.getCitiesTable().getIdCity(weather.city.locality),
            weather.temperature,
            weather.feels_like,
            weather.condition,
            weather.icon,
            weather.wind_speed,
            weather.wind_dir,
            weather.pressure_mm
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
fun getCurrentTime(): String {
    return Calendar.getInstance().time.toGMTString()
}

