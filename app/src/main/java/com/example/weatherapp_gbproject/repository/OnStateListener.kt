package com.example.weatherapp_gbproject.repository

import com.example.weatherapp_gbproject.viewmodel.state.ResponseState

fun interface OnStateListener {
    fun presentResponse(state: ResponseState)
}