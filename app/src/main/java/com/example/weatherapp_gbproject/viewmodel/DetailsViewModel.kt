package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.*



class DetailsViewModel(
    private val liveDate: MutableLiveData<DetailsWeatherState> = MutableLiveData(),
    private val repository: DetailsWeatherRepository = DetailsWeatherRepositoryRetrofitImpl()
) : ViewModel() {

    fun getLivedata() = liveDate

    fun getWeather(city: City) {
        liveDate.postValue(DetailsWeatherState.Loading)
        repository.getWeatherDetails(
            city
        ) { weatherInfo -> liveDate.postValue(
            DetailsWeatherState.Success(weatherInfo)) }
    }

    fun interface Callback {
        fun onResponse(weatherInfo: WeatherInfo)
        //TODO onFail()
    }

    fun getLatCurrentLocality(currentLocality: String): Double {
        var lat = 0.0
        for (i in 0 until getWorldCities().size) {
            if (getWorldCities()[i].city.locality == currentLocality) {
                lat = getWorldCities()[i].city.lat
                break
            } else {
                for (j in 0 until getRussianCities().size) {
                    if (getRussianCities()[j].city.locality == currentLocality) {
                        lat = getRussianCities()[j].city.lat
                        break
                    }
                }
            }
        }
        return lat
    }

    fun getLonCurrentLocality(currentLocality: String): Double {
        var lon = 0.0
        for (i in 0 until getWorldCities().size) {
            if (getWorldCities()[i].city.locality == currentLocality) {
                lon = getWorldCities()[i].city.lon
                break
            } else {
                for (j in 0 until getRussianCities().size) {
                    if (getRussianCities()[j].city.locality == currentLocality) {
                        lon = getRussianCities()[j].city.lon
                        break
                    }
                }
            }
        }
        return lon
    }
}


