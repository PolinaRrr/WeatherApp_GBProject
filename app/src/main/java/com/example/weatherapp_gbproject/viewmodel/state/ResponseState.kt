package com.example.weatherapp_gbproject.viewmodel.state

sealed class ResponseState {
    data class ErrorConnectionFromServer(val error: Exception) : ResponseState()
    data class ErrorConnectionFromClient(val error: Exception) : ResponseState()
    data class ErrorJson(val error: Exception) : ResponseState()
    data class ErrorFatal(val error: Exception) : ResponseState()
}