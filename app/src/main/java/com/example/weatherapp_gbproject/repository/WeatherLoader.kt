package com.example.weatherapp_gbproject.repository

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.BuildConfig.WEATHER_API_KEY
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class WeatherLoader(private val onServerResponseListener: OnServerResponse, private val onErrorListener: OnErrorListener) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loaderWeather(lat: Double, lon: Double) {
        val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
        // val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)
        val urlConnection: HttpsURLConnection = uri.openConnection() as HttpsURLConnection
        val responseCode = urlConnection.responseCode
        try {
            Thread {
                    urlConnection.addRequestProperty(
                        "X-Yandex-API-Key",
                        WEATHER_API_KEY
                    )

                    val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponce(weatherDTO)
                    }

            }.start()
        } catch (e: Exception) {
            if (responseCode in 300..399){
                onErrorListener.onError(ResponseState.ErrorConnectionFromClient)
            }
            onErrorListener.onError(ResponseState.ErrorConnectionFromClient)
        } finally {
            urlConnection.disconnect()
        }
    }
}