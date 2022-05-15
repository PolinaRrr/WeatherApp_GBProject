package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.repository.connection.DetailsWeatherRepositoryRetrofitImpl
import com.example.weatherapp_gbproject.viewmodel.state.DetailsWeatherState
import com.example.weatherapp_gbproject.viewmodel.state.ResponseState


class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsWeatherState> = MutableLiveData(),
    private val repository: DetailsWeatherRepository = DetailsWeatherRepositoryRetrofitImpl(),
    private val repositoryAdd: WeatherRepositoryAdd = DetailsWeatherRepositoryRoomImpl()
) : ViewModel() {

    fun getLivedata() = liveDate

    fun getWeather(city: City) {
        liveDate.postValue(DetailsWeatherState.Loading)
        repository.getWeatherDetails(
            city,
            { weatherInfo ->
                liveDate.postValue(
                    DetailsWeatherState.Success(weatherInfo)
                )
                //тут записываем в историю
                repositoryAdd.addWeather(weatherInfo)

            },
            { responseState ->
                liveDate.postValue(
                    DetailsWeatherState.Error(responseState)
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



