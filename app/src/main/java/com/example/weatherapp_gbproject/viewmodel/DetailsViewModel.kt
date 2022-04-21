package com.example.weatherapp_gbproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_gbproject.repository.getRussianCities
import com.example.weatherapp_gbproject.repository.getWorldCities


class DetailsViewModel(
    private val liveDate: MutableLiveData<ResponseState> = MutableLiveData(),
) : ViewModel() {

    fun getDataFromServer() = liveDate

    fun getLatCurrentLocality(currentLocality: String) : Double {
        var lat: Double = 0.0
        for (i in 0 until getWorldCities().size) {
            if (getWorldCities()[i].city.locality == currentLocality) {
                lat = getWorldCities()[i].city.lat
                break
            }else{
                for (j in 0 until getRussianCities().size){
                    if (getRussianCities()[j].city.locality == currentLocality){
                        lat = getRussianCities()[j].city.lat
                        break
                    }
                }
            }
        }

        return lat
    }

    fun getLonCurrentLocality(currentLocality: String) : Double {
        var lon: Double = 0.0
        for (i in 0 until getWorldCities().size) {
            if (getWorldCities()[i].city.locality == currentLocality) {
                lon = getWorldCities()[i].city.lon
                break
            }else{
                for (j in 0 until getRussianCities().size){
                    if (getRussianCities()[j].city.locality == currentLocality){
                        lon = getRussianCities()[j].city.lon
                        break
                    }
                }
            }
        }
        return lon
    }

}