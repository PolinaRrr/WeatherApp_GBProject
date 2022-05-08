package com.example.weatherapp_gbproject.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp_gbproject.R
import com.example.weatherapp_gbproject.repository.ConnectionBroadcastReceiver
import com.example.weatherapp_gbproject.repository.KEY_BUNDLE_ACTIVITY_MSG


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, WeatherListFragment.newInstance()).commit()
        }
        startService(Intent(this, AirplaneModeService::class.java).apply {
            putExtra(KEY_BUNDLE_ACTIVITY_MSG, "AirplaneMode no changes")

        })

        val receiver = ConnectionBroadcastReceiver()
        registerReceiver(receiver, IntentFilter("android.intent.action.AIRPLANE_MODE"))
    }
}