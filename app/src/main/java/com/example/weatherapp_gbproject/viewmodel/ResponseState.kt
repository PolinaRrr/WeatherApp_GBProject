package com.example.weatherapp_gbproject.viewmodel

sealed class ResponseState {
    data class ErrorConnectionFromServer(val error: Throwable) : ResponseState()
    data class ErrorConnectionFromClient(val error: Throwable) : ResponseState()
    data class ErrorJson(val error: Throwable): ResponseState()
}