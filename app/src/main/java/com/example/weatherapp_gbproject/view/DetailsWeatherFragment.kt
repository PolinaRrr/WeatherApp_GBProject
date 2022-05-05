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
import com.example.weatherapp_gbproject.databinding.FragmentDetailsWeatherBinding
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.repository.dto.WeatherDTO
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.DetailsWeatherState
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.android.material.snackbar.Snackbar


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

    private lateinit var currentLocality: City

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLivedata().observe(viewLifecycleOwner, object : Observer<DetailsWeatherState> {
            override fun onChanged(t: DetailsWeatherState) {
                renderWeatherData(t)
            }
        })


        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            receiver,
            IntentFilter(KEY_NOTIFICATION_BROADCAST_RECEIVER_WAVE)
        )
        arguments?.let { requireArguments().getParcelable<WeatherInfo>(KEY_BUNDLE_WEATHER) }
            ?.run {
                currentLocality = this.city
                viewModel.getWeather(currentLocality)
            }
    }

    private fun renderWeatherData(detailsWeatherState: DetailsWeatherState) {
        when (detailsWeatherState) {
            is DetailsWeatherState.Error -> TODO()
            DetailsWeatherState.Loading -> TODO()
            is DetailsWeatherState.Success -> {
                val weather = detailsWeatherState.weather
                with(binding) {
                    binding.loadingLayout.visibility = View.GONE
                    binding.textViewLocality.text = weather.city.locality
                    binding.textViewCondition.text = weather.condition
                    binding.textViewTemperature.text = weather.temp.toString()
                    binding.textViewTemperatureFeelLike.text = weather.feels_like.toString()
                    binding.textViewWindSpeed.text = weather.wind_speed.toString()
                    binding.textViewWindDir.text = weather.wind_dir
                    binding.textViewPressureMm.text = weather.pressure_mm.toString()
                }
            }
        }

    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { intent ->
                intent.getParcelableExtra<WeatherDTO>(KEY_BUNDLE_SERVICE_BROADCAST_WEATHER)?.let {
                    onResponse(it)
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

    override fun onResponse(weatherDTO: WeatherDTO) {
        //renderWeatherData(weatherDTO)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun presentResponse(state: ResponseState) {
        with(binding) {
            when (state) {
                is ResponseState.ErrorConnectionFromClient -> {
                    Snackbar.make(root, "Error Client", Snackbar.LENGTH_LONG).setAction("RETRY") {
                        viewModel.getWeather(currentLocality)
                    }.show()
                }
                is ResponseState.ErrorConnectionFromServer -> {
                    Snackbar.make(root, "Error Server", Snackbar.LENGTH_LONG).setAction("RETRY") {
                        viewModel.getWeather(currentLocality)
                    }.show()
                }
                is ResponseState.ErrorJson -> {
                    Snackbar.make(root, "Connection limit exceeded", Snackbar.LENGTH_LONG)
                        .setAction("BACK") {
                            activity?.supportFragmentManager?.popBackStack()
                        }.show()
                }

            }
        }
    }
}


