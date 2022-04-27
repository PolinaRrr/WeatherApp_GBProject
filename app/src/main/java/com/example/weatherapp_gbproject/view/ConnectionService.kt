package com.example.weatherapp_gbproject.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
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

class ConnectionService(private val notification: String = "") : IntentService(notification) {

/*TODO Worker
  *  override fun doWork(): Result {
  * }
 */

    @RequiresApi(Build.VERSION_CODES.N)
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("Log.d(\"@@@\", \"do work\")", "android.util.Log")
    )
    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "do work")
        intent?.let {
            val lat = it.getDoubleExtra(KEY_CONNECTION_SERVICE_LAT, 0.0)
            val lon = it.getDoubleExtra(KEY_CONNECTION_SERVICE_LON, 0.0)

            if ((0..10).random() < 5) {
                connectServer(true, lat, lon)
            } else {
                connectServer(false, lat, lon)
            }
        }
    }

    private fun connectServer(isHTTP: Boolean, lat: Double, lon: Double) {
        val urlText = if (isHTTP) {
            "$EXPERIMENTAL_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon"
        } else {
            "$YANDEX_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon"
        }

        val uri = URL(urlText)
        val urlConnection = if (isHTTP) {
            uri.openConnection() as HttpURLConnection
        } else {
            uri.openConnection() as HttpsURLConnection
        }

        var responseCode = 200
        try {
            urlConnection.addRequestProperty(
                KEY_WEATHER_LOADER_YANDEX_QUERY,
                BuildConfig.WEATHER_API_KEY
            )
            responseCode = urlConnection.responseCode

            Log.d("@@@", "$responseCode ${urlConnection.responseMessage}")

            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val response: String = buffer.readText()
            try {
                val weatherDTO: WeatherDTO = Gson().fromJson(response, WeatherDTO::class.java)

                val msg = Intent(KEY_NOTIFICATION_BROADCAST_RECEIVER_WAVE)
                msg.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
                LocalBroadcastManager.getInstance(this).sendBroadcast(msg)

            } catch (e: JsonIOException) {
                Log.d("%%% ", response)
                ResponseState.ErrorJson(e)

            } catch (e: JsonSyntaxException) {
                Log.d("%%% ", response)
                ResponseState.ErrorJson(e)
            }

        } catch (e: IOException) {
            if (responseCode in 400..499) {
                ResponseState.ErrorConnectionFromClient(e)
            } else {
                ResponseState.ErrorConnectionFromServer(Exception())
            }

        } finally {
            urlConnection.disconnect()
        }
    }
}