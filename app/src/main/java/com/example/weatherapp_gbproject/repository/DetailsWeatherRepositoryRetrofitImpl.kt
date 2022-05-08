package com.example.weatherapp_gbproject.repository


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.DataConverter
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.view.DetailsWeatherFragment
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


class DetailsWeatherRepositoryRetrofitImpl : DetailsWeatherRepository, OnStateListener {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getWeatherDetails(city: City, callback: DetailsViewModel.Callback, errorCallback: DetailsViewModel.ErrorCallback) {
        Thread {


                val request = Retrofit.Builder().apply {
                    baseUrl(chooseDomain())
                    addConverterFactory(
                        GsonConverterFactory.create(
                            GsonBuilder().setLenient().create()
                        )
                    )
                }.build().create(WeatherAPIProvider::class.java)

            var responseCode = 0


            try {
                request.getWeatherData(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
                    .enqueue(object :
                        Callback<WeatherDTO> {
                        override fun onResponse(
                            call: Call<WeatherDTO>,
                            response: Response<WeatherDTO>
                        ) {
                            responseCode = response.code()
                            Log.d("QQQ", "ERROR $responseCode")
                            try {
                                if (response.isSuccessful) {
                                    response.body()?.let {
                                        callback.onResponse(DataConverter().convertDtoToModel(it))
                                    }
                                }
                                if (responseCode in 400..499) {
                                    errorCallback.onError(ResponseState.ErrorConnectionFromClient(Exception(response.message())))

                                    /*DetailsWeatherFragment().presentResponse(
                                        ResponseState.ErrorConnectionFromClient(Exception())
                                    )*/

                                    Log.d("@@@", "ResponseCode $responseCode")
                                }
                                if (responseCode in 500..599) {
                                    DetailsWeatherFragment().presentResponse(
                                        ResponseState.ErrorConnectionFromServer(Exception())
                                    )
                                    Log.d("@@@", "ResponseCode $responseCode")
                                }

                            }catch (e:NullPointerException){
                                Log.d("@@@", "$e ResponseCode $responseCode")

                            }
                        }

                        @RequiresApi(Build.VERSION_CODES.N)
                        override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {

                        }
                    })
            } catch (e: JsonIOException) {
                DetailsWeatherFragment().presentResponse(ResponseState.ErrorJson(e))
                Log.d("QQQ", "ERROR $e")

            } catch (e: JsonSyntaxException) {
                DetailsWeatherFragment().presentResponse(ResponseState.ErrorJson(e))
                Log.d("QQQ", "ERROR $e")
            } catch (e: NullPointerException) {
                DetailsWeatherFragment().presentResponse(ResponseState.ErrorJson(e))
                Log.d("QQQ", "ERROR $e")
            }

        }.start()
    }

    private fun chooseDomain(): String {
        return if ((0..10).random() < 10) {
            YANDEX_DOMAIN
        } else {
            EXPERIMENTAL_DOMAIN
        }
    }

    override fun presentResponse(state: ResponseState) {
        TODO("Not yet implemented")
    }
}