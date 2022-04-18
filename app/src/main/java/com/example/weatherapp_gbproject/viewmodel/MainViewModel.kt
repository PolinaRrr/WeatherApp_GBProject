package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.LocalRepositoryImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: LocalRepositoryImpl = LocalRepositoryImpl()
) : ViewModel() {

    //кастомная функция возвращает лайвдейту
    fun getData() = liveDate

    fun getWorldWeather() = getWeather(false)
    fun getRussianWeather() = getWeather(true)

    @OptIn(DelicateCoroutinesApi::class)
    private fun getWeather(isRussianCity: Boolean) {
        GlobalScope.launch {
            if ((0..10).random() < 5) {
                liveDate.postValue(AppState.Error(ExceptionInInitializerError()))
            } else {
                if (isRussianCity) {
                    liveDate.postValue(AppState.Success(repository.getRussianWeatherLocalStorage()))
                } else {
                    liveDate.postValue(AppState.Success(repository.getWorldWeatherLocalStorage()))
                }
            }
        }
    }
}