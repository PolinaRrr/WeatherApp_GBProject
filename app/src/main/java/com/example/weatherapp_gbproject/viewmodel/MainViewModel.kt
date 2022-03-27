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
    fun getData(): LiveData<AppState>{
        return liveDate
    }

    fun getWeather(){

        Thread{
            liveDate.postValue(AppState.Loading)
            //TODO answer from server and local storage

            val answerFromLocalStorage = repository.getWeatherFromLocalStorage()
            val answerFromServer = repository.getWeatherFromServer()
            // передавать ответ в аргументе
            liveDate.postValue(AppState.Success(repository.getWeatherFromServer()))
        }.start()
    }

}