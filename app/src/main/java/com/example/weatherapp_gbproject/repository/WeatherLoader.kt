package com.example.weatherapp_gbproject.repository

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.BuildConfig.WEATHER_API_KEY
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class WeatherLoader(
    private val onServerResponseListener: OnServerResponse,
    private val onStateListener: OnStateListener
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loaderWeather(lat: Double, lon: Double) {
        if ((0..10).random() < 5) {
            connectEmulatorYandex(lat, lon)
        } else {
            connectYandexWeather(lat, lon)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun connectYandexWeather(lat: Double, lon: Double) {
        Thread {
            lateinit var urlConnection: HttpURLConnection
            try {
                val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
                val uri = URL(urlText)
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    WEATHER_API_KEY
                )
                Log.d("@@@", "${urlConnection.responseCode} ${urlConnection.responseMessage}")

                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response: String = buffer.readText()
                try {
                    val weatherDTO: WeatherDTO = Gson().fromJson(response, WeatherDTO::class.java)

                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponce(weatherDTO)
                    }
                }catch (e:JsonIOException){
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))

                } catch (e: JsonSyntaxException) {
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))
                }

            } catch (e: IOException) {
                if (urlConnection.responseCode in 400..499) {
                    onStateListener.presentResponse(
                        ResponseState.ErrorConnectionFromClient(e))
                } else {
                    onStateListener.presentResponse(
                        ResponseState.ErrorConnectionFromServer(
                            Exception()
                        )
                    )
                }

            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun connectEmulatorYandex(lat: Double, lon: Double) {
        Thread {
            lateinit var urlConnection: HttpURLConnection
            try {
                val urlText = "http://212.86.114.27/v2/informers?lat=$lat&lon=$lon"
                val uri = URL(urlText)
                urlConnection = uri.openConnection() as HttpURLConnection
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    WEATHER_API_KEY
                )
                Log.d("@@@", "${urlConnection.responseCode} ${urlConnection.responseMessage}")
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response: String = buffer.readText()
                try {
                    val weatherDTO: WeatherDTO = Gson().fromJson(response, WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponce(weatherDTO)
                    }
                } catch (e: JsonSyntaxException) {
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))

                } catch (e: JsonIOException) {
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))
                }

            } catch (e: IOException) {
                if (urlConnection.responseCode in 400..499) {
                    onStateListener.presentResponse(
                        ResponseState.ErrorConnectionFromClient(e))
                } else {
                    onStateListener.presentResponse(
                        ResponseState.ErrorConnectionFromServer(
                            Exception()
                        )
                    )
                }

            } finally {
                urlConnection.disconnect()

            }
        }.start()

    }
}