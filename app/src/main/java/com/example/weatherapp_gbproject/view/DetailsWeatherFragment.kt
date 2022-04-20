package com.example.weatherapp_gbproject.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.databinding.FragmentDetailsWeatherBinding
import com.example.weatherapp_gbproject.repository.*
import com.example.weatherapp_gbproject.viewmodel.DetailsViewModel
import com.example.weatherapp_gbproject.viewmodel.ResponseState
import com.google.android.material.snackbar.Snackbar



class DetailsWeatherFragment : Fragment(), OnServerResponse, OnErrorListener {

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
        Thread {
            WeatherLoader(this@DetailsWeatherFragment, this@DetailsWeatherFragment).loaderWeather(
                lat,
                lon
            )
        }.start()
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

        arguments?.let { requireArguments().getParcelable<WeatherInfo>(KEY_BUNDLE_WEATHER) }
            ?.run {
                currentLocality = this.city.locality

                startWeatherLoader(this.city.lat,this.city.lon)
//
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = DetailsWeatherFragment()
            .apply { arguments = bundle }
    }

    override fun onResponce(weatherDTO: WeatherDTO) {
        renderWeatherData(weatherDTO)
    }

    //Если я хочу сделать у Snackbar возможность повторной попытки отправки запроса и по идее нужно повторно вызвать startWeatherLoader()?

    @RequiresApi(Build.VERSION_CODES.N)
    override fun presentResponse(error: ResponseState) {
        with(binding) {
            when (error) {
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
                is ResponseState.Success -> {
                   // Snackbar.make(root, "Success", Snackbar.LENGTH_LONG).show()
                    Snackbar.make(root, "Error Client", Snackbar.LENGTH_LONG).setAction("RETRY") {
                        startWeatherLoader(
                            DetailsViewModel().getLatCurrentLocality(currentLocality),
                            DetailsViewModel().getLonCurrentLocality(currentLocality)
                        )
                    }.show()
                }
            }
        }
    }
}


