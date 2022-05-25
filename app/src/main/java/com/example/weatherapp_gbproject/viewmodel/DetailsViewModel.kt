package com.example.weatherapp_gbproject.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.repository.connection.DetailsWeatherRepositoryRetrofitImpl
import com.example.weatherapp_gbproject.viewmodel.state.DetailsWeatherState
import com.example.weatherapp_gbproject.viewmodel.state.ResponseState


class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsWeatherState> = MutableLiveData(),
    private val historyWeatherRepository: WeatherRepositoryAdd = DetailsWeatherRepositoryRoomImpl()
) : ViewModel() {

    private var actualWeatherRepository: DetailsWeatherRepository = DetailsWeatherRepositoryRetrofitImpl()

    fun getLivedata() = liveDate

    @RequiresApi(Build.VERSION_CODES.M)
    fun getWeather(city: City) {
        liveDate.postValue(DetailsWeatherState.Loading)
        /*
        Проверка на наличие соединения тут не нужна, потому что она не даст 100% верного ответа,что мы получим погоду с сервера.
        Есть только 2 варианта развития событий: или смогли подключиться к серверу яндекса и получили погоду, или нет.
        Все кейсы, когда мы не смогли подключиться обрабатываются в DetailsWeatherRepositoryRetrofitImpl через catch или по  responseCode
         */
        actualWeatherRepository.getWeatherDetails(
            city,
            { weatherInfo ->
                liveDate.postValue(
                    DetailsWeatherState.Success(weatherInfo)
                )
                historyWeatherRepository.addWeather(weatherInfo)
            },
            { responseState ->
                historyWeatherRepository.getWeatherDetails(
                    city,
                    { weatherInfo ->
                        liveDate.postValue(
                            DetailsWeatherState.Success(weatherInfo)
                        )
                    },
                    { responseStateNew ->
                        liveDate.postValue(
                            DetailsWeatherState.Error(responseStateNew)
                        )
                    }
                )
            }
        )
    }

    fun interface Callback {
        fun onResponse(weatherInfo: WeatherInfo)
    }

    fun interface ErrorCallback {
        fun onError(responseState: ResponseState)
    }
}



