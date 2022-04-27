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
        connectServer("$YANDEX_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon", false)

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun connectEmulatorYandex(lat: Double, lon: Double) {
        connectServer("$EXPERIMENTAL_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon", true)

    }
    

    private fun connectServer(url: String, isHTTP: Boolean) {
        var responseCode = 200
        Thread {
            val urlText = "url"
            val uri = URL(urlText)
            val urlConnection = if (isHTTP) {
                uri.openConnection() as HttpURLConnection
            } else {
                uri.openConnection() as HttpsURLConnection
            }

            try {
                urlConnection.addRequestProperty(
                    KEY_WEATHER_LOADER_YANDEX_QUERY,
                    WEATHER_API_KEY
                )
                responseCode = urlConnection.responseCode

                Log.d("@@@", "$responseCode ${urlConnection.responseMessage}")

                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val response: String = buffer.readText()
                try {
                    val weatherDTO: WeatherDTO = Gson().fromJson(response, WeatherDTO::class.java)

                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponce(weatherDTO)
                    }
                } catch (e: JsonIOException) {
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))

                } catch (e: JsonSyntaxException) {
                    Log.d("%%% ", response)
                    onStateListener.presentResponse(ResponseState.ErrorJson(e))
                }

            } catch (e: IOException) {
                if (responseCode in 400..499) {
                    onStateListener.presentResponse(
                        ResponseState.ErrorConnectionFromClient(e)
                    )
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