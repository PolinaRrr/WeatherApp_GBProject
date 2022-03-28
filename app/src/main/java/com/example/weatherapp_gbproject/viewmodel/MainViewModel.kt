package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.RepositoryImpl

class MainViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    //кастомная функция возвращает лайвдейту
    fun getData(): LiveData<AppState> {
        return liveDate
    }

    fun getWeather() {

        Thread {
            liveDate.postValue(AppState.Loading)
            if ((0..10).random() < 5) {
                liveDate.postValue(AppState.Error(ExceptionInInitializerError()))
            } else {
                if ((0..10).random() < 10) {
                    liveDate.postValue(AppState.Success(repository.getWeatherFromServer()))
                } else {
                    liveDate.postValue(AppState.Success(repository.getWeatherFromLocalStorage()))
                }
            }
        }.start()
    }

}