package com.example.weatherapp_gbproject.repository


import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel.Callback
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class DetailsWeatherRepositoryOkHttpImpl : DetailsWeatherRepository {
    override fun getWeatherDetails(city: City, callback: Callback){
        val client = OkHttpClient()
        val builderRequest = Request.Builder()
        builderRequest.addHeader(KEY_WEATHER_LOADER_YANDEX_QUERY, BuildConfig.WEATHER_API_KEY)
        builderRequest.url("$YANDEX_DOMAIN${YANDEX_TARIFF_VERSION}lat=${city.lat}&lon=${city.lon}")
        val request = builderRequest.build()
        val call = client.newCall(request)
        Thread{
            val response: Response = call.execute()
            if (response.isSuccessful) {
                val weatherDTO: WeatherDTO =
                    Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                val weather = DataConverter().convertDtoToModel(weatherDTO)
                weather.city = city
                callback.onResponse(weather)

            } else {
                TODO("400 / 500 errors")
            }
        }.start()

    }
}