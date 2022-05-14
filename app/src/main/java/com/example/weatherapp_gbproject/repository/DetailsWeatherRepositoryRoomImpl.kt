package com.example.weatherapp_gbproject.repository


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.WeatherApp
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel

class DetailsWeatherRepositoryRoomImpl : DetailsWeatherRepository, WeatherRepository, WeatherRepositoryAdd {
    override fun getWeatherDetails(
        city: City,
        callback: DetailsViewModel.Callback,
        errorCallback: DetailsViewModel.ErrorCallback
    ) {
        val list = DataConverter().convertHistoryTableToWeather(
            WeatherApp.getHistoryWeatherTable()
                .getHistoryForCity(WeatherApp.getCitiesTable().getIdCity(city.locality))
        )
        callback.onResponse(list.last())
    }

    override fun getWeather(callback: HistoryViewModel.CallbackFullInfo) {
        Thread{
            callback.onResponse(

                DataConverter().convertHistoryTableToWeather(
                    WeatherApp.getHistoryWeatherTable().getInfo()
                )
            )
        }.start()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun addWeather(weatherInfo: WeatherInfo) {
        WeatherApp.getHistoryWeatherTable().insert(DataConverter().convertWeatherToHistoryTable(weatherInfo))
    }

}