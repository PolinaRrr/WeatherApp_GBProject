package com.example.weatherapp_gbproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp_gbproject.viewmodel.MainViewModel
import com.example.weatherapp_gbproject.databinding.FragmentMainBinding
import com.example.weatherapp_gbproject.viewmodel.AppState
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        //на старте запрашивает данные о погоде
        viewModel.getWeather()
    }

    fun renderData(data: AppState?) {
        when (data) {
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                binding.let {
                    Snackbar.make(it.mainView, "FATAL ERROR  ${data.error}", Snackbar.LENGTH_LONG)
                        .setAction(
                            "RETRY"
                        ) { ViewModelProvider(this).get(MainViewModel::class.java).getWeather() }
                        .show()
                }

            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.textViewLocality.text = data.weatherInfo.city.locality
                binding.textViewCondition.text = data.weatherInfo.condition
                binding.textViewTemperature.text = "${data.weatherInfo.temp.toString() + "℃"}"
                binding.textViewTemperatureFeelLike.text =
                    "${data.weatherInfo.feels_like.toString() + "℃"}"
                binding.textViewWindSpeed.text =
                    "${data.weatherInfo.wind_speed.toString() + "mps"}"
                binding.textViewWindDir.text = data.weatherInfo.wind_dir
                binding.textViewPressureMm.text =
                    "${data.weatherInfo.pressure_mm.toString() + "mmHg"}"

            }
            null -> binding.loadingLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}


