package com.example.weatherapp_gbproject.view.history


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_gbproject.databinding.FragmentHistoryWeatherListRecycleItemBinding
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.repository.CELSIUS_DEGREE
import com.example.weatherapp_gbproject.repository.WIND_SPEED
import com.example.weatherapp_gbproject.repository.ATMOSPHERIC_PRESSURE

class HistoryWeatherListAdapter(
    private var data: List<WeatherInfo> = listOf()
) :
    RecyclerView.Adapter<HistoryWeatherListAdapter.CityHolder>() {

    fun setData(data: List<WeatherInfo>) {
        this.data = data
        notifyDataSetChanged()
        //TODO:  DiffUtil
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentHistoryWeatherListRecycleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) = holder.bind(data[position])


    override fun getItemCount() = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: WeatherInfo) {
            FragmentHistoryWeatherListRecycleItemBinding.bind(itemView)
                .apply {
                    textViewCityName.text = weather.city.locality
                    textViewTemp.text = "${weather.temperature}$CELSIUS_DEGREE/ "
                    textViewFeelLikeTemp.text = "${weather.feels_like}$CELSIUS_DEGREE "
                    textViewCondition.text = weather.condition
                    textViewWindSpeed.text = "${weather.wind_speed}$WIND_SPEED "
                    textViewWindDir.text = weather.wind_dir
                    textViewPressure.text = "${weather.pressure_mm}$ATMOSPHERIC_PRESSURE "
                }
        }
    }
}