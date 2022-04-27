package com.example.weatherapp_gbproject.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.weatherapp_gbproject.BuildConfig
import com.example.weatherapp_gbproject.databinding.FragmentDetailsWeatherBinding
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class DetailsWeatherFragment : Fragment(), OnServerResponse, OnStateListener {

    private var _binding: FragmentDetailsWeatherBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var currentLocality: String

    @RequiresApi(Build.VERSION_CODES.N)
    fun startWeatherLoader(lat: Double, lon: Double) {
//        requireActivity().startService(
//            Intent(
//                requireContext(),
//                ConnectionService::class.java
//            ).apply {
//                putExtra(KEY_CONNECTION_SERVICE_LAT, lat)
//                putExtra(KEY_CONNECTION_SERVICE_LON, lon)
//            })
        binding.loadingLayout.visibility = View.VISIBLE

        val client = OkHttpClient()
        val builderRequest = Request.Builder()
        builderRequest.addHeader(KEY_WEATHER_LOADER_YANDEX_QUERY, BuildConfig.WEATHER_API_KEY)
        builderRequest.url("$YANDEX_DOMAIN${YANDEX_TARIFF_VERSION}lat=$lat&lon=$lon")
        val request = builderRequest.build()
        val callback:Callback = object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                //TODO HW
            binding.loadingLayout.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val weatherDTO: WeatherDTO = Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                    requireActivity().runOnUiThread{
                        renderWeatherData(weatherDTO)
                    }
                }else{
                    TODO("400 / 500 errors")
                }
            }
        }
        val call = client.newCall(request)
        call.enqueue(callback)
    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer =
            Observer<ResponseState> { error -> presentResponse(error) }
        viewModel.getDataFromServer().observe(viewLifecycleOwner, observer)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver,
            IntentFilter(KEY_NOTIFICATION_BROADCAST_RECEIVER_WAVE)
        )
        arguments?.let { requireArguments().getParcelable<WeatherInfo>(KEY_BUNDLE_WEATHER) }
            ?.run {
                currentLocality = this.city.locality
                startWeatherLoader(this.city.lat, this.city.lon)
            }
    }

    private fun renderWeatherData(weather: WeatherDTO) {
        with(binding) {
            binding.loadingLayout.visibility = View.GONE
            binding.textViewLocality.text = currentLocality
            binding.textViewCondition.text = weather.fact.condition
            binding.textViewTemperature.text = weather.fact.temp.toString()
            binding.textViewTemperatureFeelLike.text = weather.fact.feelsLike.toString()
            binding.textViewWindSpeed.text = weather.fact.windSpeed.toString()
            binding.textViewWindDir.text = weather.fact.windDir
            binding.textViewPressureMm.text = weather.fact.pressureMm.toString()
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let {
                    onResponce(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = DetailsWeatherFragment()
            .apply { arguments = bundle }
    }

    override fun onResponce(weatherDTO: WeatherDTO) {
        renderWeatherData(weatherDTO)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun presentResponse(state: ResponseState) {
        with(binding) {
            when (state) {
                is ResponseState.ErrorConnectionFromClient -> {
                    Snackbar.make(root, "Error Client", Snackbar.LENGTH_LONG).setAction("RETRY") {
                        startWeatherLoader(
                            DetailsViewModel().getLatCurrentLocality(currentLocality),
                            DetailsViewModel().getLonCurrentLocality(currentLocality)
                        )
                    }.show()
                }
                is ResponseState.ErrorConnectionFromServer -> {
                    Snackbar.make(root, "Error Server", Snackbar.LENGTH_LONG).setAction("RETRY") {
                        startWeatherLoader(
                            DetailsViewModel().getLatCurrentLocality(currentLocality),
                            DetailsViewModel().getLonCurrentLocality(currentLocality)
                        )
                    }.show()
                }
                is ResponseState.ErrorJson -> {
                    Snackbar.make(root, "Connection limit exceeded", Snackbar.LENGTH_LONG)
                        .setAction("BACK") {
                            activity?.supportFragmentManager?.popBackStack()
                        }.show()
                }
                is ResponseState.Success -> {
                    Snackbar.make(root, "Success", Snackbar.LENGTH_LONG).show()

                }
            }
        }
    }
}


