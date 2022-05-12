package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.DetailsWeatherRepositoryRoomImpl
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.viewmodel.state.AppState

class HistoryViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: DetailsWeatherRepositoryRoomImpl = DetailsWeatherRepositoryRoomImpl()
) : ViewModel() {

    fun getData():LiveData<AppState> {
        return liveDate
    }

   fun getFullWeatherInfo(){
       repository.getWeather(object: CallbackFullInfo{
           override fun onResponse(listWeatherInfo: List<WeatherInfo>) {
               liveDate.postValue(AppState.Success(listWeatherInfo))
           }
       })
   }

    interface CallbackFullInfo{
        fun onResponse(listWeatherInfo: List<WeatherInfo>)
    }
}