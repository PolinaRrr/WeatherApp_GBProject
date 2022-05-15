package com.example.weatherapp_gbproject.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.databinding.FragmentHistoryWeatherListBinding
import com.example.weatherapp_gbproject.view.list.OnItemListClickListener
import com.example.weatherapp_gbproject.viewmodel.HistoryViewModel
import com.example.weatherapp_gbproject.viewmodel.state.AppState

class HistoryWeatherListFragment : Fragment(),OnItemListClickListener {

    private var _binding: FragmentHistoryWeatherListBinding? = null
    private val binding get() = _binding!!


    private val weatherHistoryListAdapter: HistoryWeatherListAdapter by lazy {
        HistoryWeatherListAdapter()
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

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer =
            Observer<AppState> { data -> renderData(data) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getFullWeatherInfo()
    }

    private fun renderData(data: AppState) {
        with(binding) {
            when (data) {
                is AppState.Error -> {

                }
                is AppState.Loading -> {

                }
                is AppState.Success -> {

                    weatherHistoryListAdapter.run { setData(data.weatherInfoList) }
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




