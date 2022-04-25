package com.example.weatherapp_gbproject.view

import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.repository.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ConnectionService(private val notification: String = "") : IntentService(notification) {

//TODO Worker
//    override fun doWork(): Result {
//
//    }

    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("Log.d(\"@@@\", \"do work\")", "android.util.Log")
    )
    override fun onHandleIntent(intent: Intent?) {
        Log.d("@@@", "do work")
        intent?.let {
            val lat = it.getDoubleExtra(KEY_CONNECTION_SERVICE_LAT,0.0)
            val lon = it.getDoubleExtra(KEY_CONNECTION_SERVICE_LON,0.0)
            Log.d("@@@", "work $lat $lon")


            val urlText = "$EXPERIMENTAL_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon"
            val uri = URL(urlText)
            val urlConnection: HttpURLConnection = uri.openConnection() as HttpURLConnection
            urlConnection.addRequestProperty(
                KEY_WEATHER_LOADER_YANDEX_QUERY,
                BuildConfig.WEATHER_API_KEY
            )
            //Log.d("@@@", "${urlConnection.responseCode} ${urlConnection.responseMessage}")

            val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val response: String = buffer.readText()

            val weatherDTO: WeatherDTO = Gson().fromJson(response, WeatherDTO::class.java)


            val msg = Intent(KEY_NOTIFICATION_BROADCAST_RECEIVER_WAVE)
            msg.putExtra(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER, weatherDTO)
            LocalBroadcastManager.getInstance(this).sendBroadcast(msg)

        }
    }
}