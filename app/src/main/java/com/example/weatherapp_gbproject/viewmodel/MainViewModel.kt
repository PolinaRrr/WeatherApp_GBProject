package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp_gbproject.repository.RepositoryImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(
    private val liveDate: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl()
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