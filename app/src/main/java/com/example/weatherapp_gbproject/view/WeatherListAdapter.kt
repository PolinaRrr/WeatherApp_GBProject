package com.example.weatherapp_gbproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp_gbproject.repository.WeatherInfo
import com.example.weatherapp_gbproject.databinding.FragmentWeatherListRecycleItemBinding
import com.example.weatherapp_gbproject.R
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_WEATHER

class WeatherListAdapter(
    private val onItemListClickListener: OnItemListClickListener,
    private var data: List<WeatherInfo> = listOf()
) :
    RecyclerView.Adapter<WeatherListAdapter.CityHolder>() {

    fun setData(data: List<WeatherInfo>) {
        this.data = data
        notifyDataSetChanged()
        //TODO:diffutil
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val binding = FragmentWeatherListRecycleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weather: WeatherInfo) {
            //TODO apply
            val binding = FragmentWeatherListRecycleItemBinding.bind(itemView)
            binding.textViewCityName.text= weather.city.locality
            binding.root.setOnClickListener {
                onItemListClickListener.OnItemListClick(weather)
            }
        }
    }
}