package com.example.weatherapp_gbproject.view.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.R
import com.example.weatherapp_gbproject.databinding.FragmentWeatherListBinding
import com.example.weatherapp_gbproject.repository.APP_PREFERENCES
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_WEATHER
import com.example.weatherapp_gbproject.repository.PREFERENCES_RUSSIAN_LOCALITY
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.view.details.DetailsWeatherFragment
import com.example.weatherapp_gbproject.viewmodel.MainViewModel
import com.example.weatherapp_gbproject.viewmodel.state.AppState
import com.google.android.material.snackbar.Snackbar

class WeatherListFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!


    private val weatherListAdapter: WeatherListAdapter by lazy {
        WeatherListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var isRussian = true

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        val observer =
            Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
    }


    private fun saveLocalityPreferences(isRussian: Boolean) {
        val localityPreferences =
            activity?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val editor = localityPreferences?.edit()
        editor?.putBoolean(PREFERENCES_RUSSIAN_LOCALITY, isRussian)
        editor?.apply()
    }

    private fun loadLocalityPreferences() {
        activity?.let {
            if (it.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
                    .getBoolean(PREFERENCES_RUSSIAN_LOCALITY, isRussian)
            ) {
                renderLocality()
            } else {
                viewModel.getRussianWeather()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = weatherListAdapter
        loadLocalityPreferences()
        binding.floatingActionButton.setOnClickListener {
            saveLocalityPreferences(isRussian)
            renderLocality()
        }
    }

    private fun renderLocality() {
        isRussian = !isRussian
        if (isRussian) {
            viewModel.getRussianWeather()
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.icon_russia_flag
                )
            )
        } else {
            viewModel.getWorldWeather()
            binding.floatingActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.icon_weather_world
                )
            )
        }
    }


    private fun renderData(data: AppState) {
        with(binding) {
            when (data) {
                is AppState.Error -> {
                    loadingLayout.visibility = View.GONE
                    root.setRetry("FATAL ERROR", "RETRY")
                }
                is AppState.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Success -> {
                    loadingLayout.visibility = View.GONE
                    weatherListAdapter.run { setData(data.weatherInfoList) }
                }
            }
        }
    }

    private fun View.setRetry(textMessage: String, textRetry: String) {
        Snackbar.make(
            this,
            textMessage,
            Snackbar.LENGTH_LONG
        ).setAction(
            textRetry
        ) {
            renderLocality()
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun onItemListClick(weather: WeatherInfo) {
        activity?.run {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.main_container,
                    DetailsWeatherFragment.newInstance(
                        Bundle().apply {
                            putParcelable(KEY_BUNDLE_WEATHER, weather)
                        })
                )
                .addToBackStack("")
                .commit()
        }
    }
}




