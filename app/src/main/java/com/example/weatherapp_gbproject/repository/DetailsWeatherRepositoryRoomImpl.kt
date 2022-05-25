package com.example.weatherapp_gbproject.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel
import com.example.weatherapp_gbproject.room.HistoryManager
import com.example.weatherapp_gbproject.viewmodel.state.ResponseState
import java.net.ConnectException


class DetailsWeatherRepositoryRoomImpl : DetailsWeatherRepository, WeatherRepository,
    WeatherRepositoryAdd {
    override fun getWeatherDetails(
        city: City,
        callback: DetailsViewModel.Callback,
        errorCallback: DetailsViewModel.ErrorCallback
    ) {
        Thread{
            val list =
                DataConverter().convertHistoryToWeather(HistoryManager().getLastHistoryFromTables())
            if (list.isEmpty()) {
                errorCallback.onError(ResponseState.ErrorFatal(ConnectException()))
            } else {
                callback.onResponse(list.last())
            }
        }.start()
    }

    override fun getWeather(callback: HistoryViewModel.CallbackFullInfo) {
        Thread {
            callback.onResponse(
                DataConverter().convertHistoryToWeather(HistoryManager().getLastHistoryFromTables())
            )
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun addWeather(weatherInfo: WeatherInfo) {
        Thread {
            HistoryManager().putEntryToHistoryTable(
                DataConverter().convertWeatherToHistory(
                    weatherInfo
                )
            )
        }.start()
    }

}