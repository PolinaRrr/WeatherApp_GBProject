package com.example.weatherapp_gbproject.repository


import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsWeatherRepositoryRetrofitImpl : DetailsWeatherRepository {
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {
        val request = Retrofit.Builder().apply {
            baseUrl(YANDEX_DOMAIN)
            addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
        }.build().create(WeatherAPIProvider::class.java)
        //request.getWeatherData(WEATHER_API_KEY,city.lat,city.lon).execute()
        request.getWeatherData(BuildConfig.WEATHER_API_KEY,city.lat,city.lon).enqueue(object :
            Callback<WeatherDTO>{
            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                if (response.isSuccessful){
                    response.body()?.let {
                        callback.onResponse(DataConverter().convertDtoToModel(it))
                    }
                }
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}