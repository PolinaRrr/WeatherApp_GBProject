package com.example.weatherapp_gbproject.viewmodel

sealed class ResponseState {
    object ErrorConnectionFromClient: ResponseState()
    data class ErrorConnectionFromServer( val error:Throwable):ResponseState()
}