package com.example.weatherapp_gbproject.view.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_gbproject.databinding.FragmentHistoryWeatherListRecycleItemBinding
import com.example.weatherapp_gbproject.repository.WeatherInfo


class WeatherListAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var data: List<WeatherInfo> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

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
                .apply { textViewCityName.text = weather.city.locality }
                .run {
                    root.setOnClickListener {
                        onItemListClickListener.onItemListClick(weather)
                    }
                }
        }
    }
}