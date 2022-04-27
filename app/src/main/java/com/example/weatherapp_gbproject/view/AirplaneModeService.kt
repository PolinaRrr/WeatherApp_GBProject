package com.example.weatherapp_gbproject.view

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_ACTIVITY_MSG


class AirplaneModeService(private val notification: String = "") : IntentService(notification) {

    @Deprecated("Deprecated in Java",
        ReplaceWith("Log.d(\"@@@\", \"do work GPS\")", "android.util.Log")
    )
    override fun onHandleIntent(intent: Intent?) {
     Log.d("@@@", "Start service AirplaneMode")
        intent?.let {
            val str = it.getStringExtra(KEY_BUNDLE_ACTIVITY_MSG)
            Log.d("@@@", "$str")
        }
    }
}
