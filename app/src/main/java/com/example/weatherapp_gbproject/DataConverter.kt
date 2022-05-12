package com.example.weatherapp_gbproject

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.repository.City
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.dto.FactDTO
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.repository.getDefaultCity
import com.example.weatherapp_gbproject.room.HistoryWeatherRequest

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
                City(
                    WeatherApp.getCitiesTable().getNameCity(it.id_city),
                    WeatherApp.getCitiesTable().getLatCity(it.id_city),
                    WeatherApp.getCitiesTable().getLonCity(it.id_city)
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

    @RequiresApi(Build.VERSION_CODES.N)
    fun convertWeatherToHistoryTable(weather: WeatherInfo): HistoryWeatherRequest{
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
    return Calendar.getInstance().time.toString()
}

