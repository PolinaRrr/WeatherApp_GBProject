package com.example.weatherapp_gbproject.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel
import com.example.weatherapp_gbproject.room.HistoryManager


class DetailsWeatherRepositoryRoomImpl : DetailsWeatherRepository, WeatherRepository, WeatherRepositoryAdd {
    override fun getWeatherDetails(
        city: City,
        callback: DetailsViewModel.Callback,
        errorCallback: DetailsViewModel.ErrorCallback
    ) {
        val list = DataConverter().convertHistoryToWeather(HistoryManager().lastHistory)
        callback.onResponse(list.last())
    }

    override fun getWeather(callback: HistoryViewModel.CallbackFullInfo) {
        Thread{
            callback.onResponse(
                DataConverter().convertHistoryToWeather(HistoryManager().lastHistory)
            )
        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun addWeather(weatherInfo: WeatherInfo) {
        DataConverter().convertWeatherToHistory(weatherInfo)
    }

}