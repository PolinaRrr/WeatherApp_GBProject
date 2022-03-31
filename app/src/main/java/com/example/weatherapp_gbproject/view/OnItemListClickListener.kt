package com.example.weatherapp_gbproject.view

import com.example.weatherapp_gbproject.repository.WeatherInfo

interface OnItemListClickListener {
    fun OnItemListClick(weather: WeatherInfo){
    }
}