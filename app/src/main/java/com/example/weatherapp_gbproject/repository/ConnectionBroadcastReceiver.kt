package com.example.weatherapp_gbproject.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ConnectionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        intent?.let {
            val strExtra = it.getStringExtra(KEY_CONNECTION_SERVICE_LON)
            Log.d("@@@", "onReceive $strExtra")
        }
    }
}