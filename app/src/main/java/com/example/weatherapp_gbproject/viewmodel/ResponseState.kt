package com.example.weatherapp_gbproject.viewmodel

sealed class ResponseState {
    //object Success : ResponseState()
    data class ErrorConnectionFromServer(val error: Exception) : ResponseState()
    data class ErrorConnectionFromClient(val error: Exception) : ResponseState()
    data class ErrorJson(val error: Exception): ResponseState()
}