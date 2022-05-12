package com.example.weatherapp_gbproject

import android.app.Application
import androidx.room.Room
import com.example.weatherapp_gbproject.room.CitiesDao
import com.example.weatherapp_gbproject.room.HistoryWeatherDao
import com.example.weatherapp_gbproject.room.WeatherDB

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        private var db: WeatherDB? = null
        private var appContext: WeatherApp? = null
        fun getHistoryWeatherTable(): HistoryWeatherDao {

            if (db == null) {
                if (appContext!=null){
                    db = Room.databaseBuilder(appContext!!,WeatherDB::class.java,"weather").build()
                }else{
                    throw IllegalStateException("appContext = null")
                }
            }
            return db!!.getHistoryWeather()
        }
        fun getCitiesTable(): CitiesDao {
            if (db == null) {
                if (appContext!=null){
                    db = Room.databaseBuilder(appContext!!,WeatherDB::class.java,"weather").build()
                }else{
                    throw IllegalStateException("appContext = null")
                }
            }
            return db!!.getCities()
        }
    }


}