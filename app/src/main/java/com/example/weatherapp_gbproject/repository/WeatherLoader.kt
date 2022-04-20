package com.example.weatherapp_gbproject.repository

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.BuildConfig.WEATHER_API_KEY
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class WeatherLoader(
    private val onServerResponseListener: OnServerResponse,
    private val onErrorListener: OnErrorListener
) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun loaderWeather(lat: Double, lon: Double) {
        if ((0..10).random() < 11) {
            connectEmulatorYandex(lat, lon)
        } else {
            connectYandexWeather(lat, lon)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun connectYandexWeather(lat: Double, lon: Double) {
        val urlText = "https://api.weather.yandex.ru/v2/informers?lat=$lat&lon=$lon"
        val uri = URL(urlText)
        val urlConnection: HttpsURLConnection = uri.openConnection() as HttpsURLConnection
        try {
            Thread {
                urlConnection.addRequestProperty(
                    "X-Yandex-API-Key",
                    WEATHER_API_KEY
                )
                Log.d("@@@", "${urlConnection.responseCode} ${urlConnection.responseMessage}")

                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)

                Handler(Looper.getMainLooper()).post {
                    onServerResponseListener.onResponce(weatherDTO)
                }

            }.start()
        } catch (e: IOException) {
            onErrorListener.presentResponse(
                ResponseState.ErrorConnectionFromServer(e)
            )

        }catch (e: JsonSyntaxException) {
            onErrorListener.presentResponse(ResponseState.ErrorConnectionFromClient(e))

        }  catch (e: Exception) {
            onErrorListener.presentResponse(
                ResponseState.ErrorConnectionFromServer(e)
            )

        } finally {
            urlConnection.disconnect()
        }
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

                    //чтото в этой строке
                    val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val weatherDTO: WeatherDTO = Gson().fromJson(buffer, WeatherDTO::class.java)
                    Handler(Looper.getMainLooper()).post {
                        onServerResponseListener.onResponce(weatherDTO)
                    }
                    onErrorListener.presentResponse(
                        ResponseState.ErrorConnectionFromServer(
                            Exception()
                        )
                    )
                } catch (e: IOException) {
                    onErrorListener.presentResponse(
                        ResponseState.ErrorConnectionFromServer(
                            Exception()
                        )
                    )

                } catch (e: JsonSyntaxException) {
                    onErrorListener.presentResponse(ResponseState.ErrorConnectionFromClient(e))

                } catch (e: Exception) {
                    onErrorListener.presentResponse(
                        ResponseState.ErrorConnectionFromServer(
                            Exception()
                        )
                    )

                } finally {
                    urlConnection.disconnect()
                    onErrorListener.presentResponse(ResponseState.Success)
                }
            }.start()

    }
}