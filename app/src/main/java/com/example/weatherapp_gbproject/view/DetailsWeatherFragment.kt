package com.example.weatherapp_gbproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_WEATHER
import com.example.weatherapp_gbproject.databinding.FragmentDetailsWeatherBinding

class DetailsWeatherFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { requireArguments().getParcelable<WeatherInfo>(KEY_BUNDLE_WEATHER) }
            ?.let { renderData(it) }
    }

    private fun renderData(weather: WeatherInfo) {
        binding.loadingLayout.visibility = View.GONE
        binding.textViewLocality.text = weather.city.locality
        binding.textViewCondition.text = weather.condition
        binding.textViewTemperature.text = weather.temp.toString()
        binding.textViewTemperatureFeelLike.text = weather.feels_like.toString()
        binding.textViewWindSpeed.text = weather.wind_speed.toString()
        binding.textViewWindDir.text = weather.wind_dir
        binding.textViewPressureMm.text = weather.pressure_mm.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsWeatherFragment {
            val fragment = DetailsWeatherFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}


