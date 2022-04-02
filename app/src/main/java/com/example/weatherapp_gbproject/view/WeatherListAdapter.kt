package com.example.weatherapp_gbproject.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.databinding.FragmentWeatherListRecycleItemBinding


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
        val binding = FragmentWeatherListRecycleItemBinding.inflate(
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
            val binding = FragmentWeatherListRecycleItemBinding.bind(itemView)
                .apply { textViewCityName.text = weather.city.locality }
            binding.root.setOnClickListener {
                onItemListClickListener.onItemListClick(weather)
            }
        }
    }
}