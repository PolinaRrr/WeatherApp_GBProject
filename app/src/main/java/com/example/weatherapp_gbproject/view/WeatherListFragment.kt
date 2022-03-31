package com.example.weatherapp_gbproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.R
import com.example.weatherapp_gbproject.viewmodel.MainViewModel
import com.example.weatherapp_gbproject.databinding.FragmentWeatherListBinding
import com.example.weatherapp_gbproject.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_WEATHER

class WeatherListFragment : Fragment(), OnItemListClickListener {

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!

    //TODO private
    val weatherListAdapter = WeatherListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        return binding.root
    }


    //TODO private
    var isRussian = true

    fun getIsRussian(): Boolean {
        return isRussian
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO HW вынести в initRecycler()
        binding.recyclerView.adapter = weatherListAdapter

        //ссылка на уже существующую вьюмодел
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        //ссылка на ответ, который в случае изменения/обновления объекта лайвдейты, вызовет рендер нового значения
        val observer = object : Observer<AppState> {
            override fun onChanged(data: AppState) {
                renderData(data)
            }
        }
        //подписка на лайвдейту мейнфрагментом, чтобы пока жив фрагмент, ловить изменения
        viewModel.getData().observe(viewLifecycleOwner, observer)

        binding.floatingActionButton.setOnClickListener {
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
                binding.floatingActionButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.icon_weather_world
                    )
                )
                viewModel.getWorldWeather()
            }
        }
        viewModel.getRussianWeather()
    }

    fun renderData(data: AppState) {
        when (data) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.let {
                    Snackbar.make(it.root, "FATAL ERROR  ${data.error}", Snackbar.LENGTH_LONG)
                        .setAction(
                            "RETRY"
                        ) {
                            if (isRussian) {
                                ViewModelProvider(this).get(MainViewModel::class.java)
                                    .getRussianWeather()
                            } else {
                                ViewModelProvider(this).get(MainViewModel::class.java)
                                    .getWorldWeather()
                            }
                        }
                        .show()
                }

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                weatherListAdapter.setData(data.weatherInfoList)


//                binding.textViewLocality.text = data.weatherInfoList.city.locality
//                binding.textViewCondition.text = data.weatherInfoList.condition
//                binding.textViewTemperature.text = "${data.weatherInfoList.temp.toString() + "℃"}"
//                binding.textViewTemperatureFeelLike.text =
//                    "${data.weatherInfoList.feels_like.toString() + "℃"}"
//                binding.textViewWindSpeed.text =
//                    "${data.weatherInfoList.wind_speed.toString() + "mps"}"
//                binding.textViewWindDir.text = data.weatherInfoList.wind_dir
//                binding.textViewPressureMm.text =
//                    "${data.weatherInfoList.pressure_mm.toString() + "mmHg"}"

            }
            //null -> binding.loadingLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherListFragment()
    }

    override fun OnItemListClick(weather: WeatherInfo) {
        val bundle = Bundle()
        bundle.putParcelable(KEY_BUNDLE_WEATHER, weather)
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.main_container, DetailsWeatherFragment.newInstance(bundle))
            .addToBackStack("").commit()
    }
}


