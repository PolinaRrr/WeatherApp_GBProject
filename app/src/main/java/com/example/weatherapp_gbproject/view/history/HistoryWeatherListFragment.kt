package com.example.weatherapp_gbproject.view.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.databinding.FragmentHistoryWeatherListBinding
import com.example.weatherapp_gbproject.repository.APP_PREFERENCES
import com.example.weatherapp_gbproject.repository.PREFERENCES_RUSSIAN_LOCALITY
import com.example.weatherapp_gbproject.view.list.OnItemListClickListener
import com.example.weatherapp_gbproject.view.list.WeatherListAdapter
import com.example.weatherapp_gbproject.viewmodel.MainViewModel
import com.example.weatherapp_gbproject.viewmodel.state.AppState

class HistoryWeatherListFragment : Fragment(),OnItemListClickListener {

    private var _binding: FragmentHistoryWeatherListBinding? = null
    private val binding get() = _binding!!


    private val weatherListAdapter: WeatherListAdapter by lazy {
        WeatherListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var isRussian = true

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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


    private fun renderData(data: AppState) {
        with(binding) {
            when (data) {
                is AppState.Error -> {

                }
                is AppState.Loading -> {

                }
                is AppState.Success -> {

                    weatherListAdapter.run { setData(data.weatherInfoList) }
                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryWeatherListFragment()
    }


}




