package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.ResponseState

fun interface OnErrorListener {
    fun presentResponse(error:ResponseState)
}