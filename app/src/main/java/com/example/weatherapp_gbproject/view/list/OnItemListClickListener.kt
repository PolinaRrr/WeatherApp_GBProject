package com.example.weatherapp_gbproject.view.list

import com.example.weatherapp_gbproject.repository.WeatherInfo

interface OnItemListClickListener {
    fun onItemListClick(weather: WeatherInfo) {
    }
}