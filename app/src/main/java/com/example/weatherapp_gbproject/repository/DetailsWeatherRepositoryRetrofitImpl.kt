package com.example.weatherapp_gbproject.repository


import android.util.Log
import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsWeatherRepositoryRetrofitImpl() : DetailsWeatherRepository {

    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback) {

        Thread{
            var responseCode = 0
            try {
                val request = Retrofit.Builder().apply {
                    baseUrl(chooseDomain())
                    addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                }.build().create(WeatherAPIProvider::class.java)

                request.getWeatherData(BuildConfig.WEATHER_API_KEY, city.lat, city.lon).enqueue(object :
                    Callback<WeatherDTO> {
                    override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                        responseCode = response.code()
                        if (response.isSuccessful) {
                            response.body()?.let {
                                callback.onResponse(DataConverter().convertDtoToModel(it))
                            }
                        }
                    }
                    override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                        if (responseCode in 400..499) {
                            ResponseState.ErrorConnectionFromClient(t)
                            Log.d("@@@","ResponseCode $responseCode")
                        }
                        if (responseCode in 500..599){
                            ResponseState.ErrorConnectionFromServer(t)
                            Log.d("@@@","ResponseCode $responseCode")
                        }
                    }
                })
            }catch (e: JsonIOException){
                ResponseState.ErrorJson(e)
                Log.d("QQQ","ERROR $e")

            }catch (e: JsonSyntaxException){
                ResponseState.ErrorJson(e)
                Log.d("QQQ","ERROR $e")
            }
        }.start()

    }

    private fun chooseDomain(): String {
        return if ((0..10).random() < 5) {
            YANDEX_DOMAIN
        } else {
            EXPERIMENTAL_DOMAIN
        }
    }
}