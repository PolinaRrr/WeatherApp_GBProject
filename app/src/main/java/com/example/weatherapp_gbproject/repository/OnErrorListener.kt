package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.ResponseState

fun interface OnErrorListener {
    fun onError(error:ResponseState)
}