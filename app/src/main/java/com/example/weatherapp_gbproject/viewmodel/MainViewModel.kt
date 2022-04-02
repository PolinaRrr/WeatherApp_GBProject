package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_gbproject.repository.RepositoryImpl

class MainViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    //кастомная функция возвращает лайвдейту
    fun getData(): LiveData<AppState> {
        return liveDate
    }

    fun getWorldWeather() = getWeather(false)
    fun getRussianWeather() = getWeather(true)

    private fun getWeather(isRussianCity: Boolean) {
        Thread {
            if ((0..10).random() < 5) {
                liveDate.postValue(AppState.Error(ExceptionInInitializerError()))
            } else {
                if (isRussianCity) {
                    liveDate.postValue(AppState.Success(repository.getRussianWeatherLocalStorage()))
                } else {
                    liveDate.postValue(AppState.Success(repository.getWorldWeatherLocalStorage()))
                }
            }
        }.start()
    }
}