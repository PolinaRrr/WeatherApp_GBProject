package com.example.weatherapp_gbproject.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection
import java.net.URL
import com.example.weatherapp_gbproject.databinding.ActivityWebviewMainBinding


class MainWebviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebviewMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonOk.setOnClickListener {

            val uri = URL(binding.editTextUrl.text.toString())
            val urlConnection: HttpsURLConnection = uri.openConnection() as HttpsURLConnection
            Thread {
                val buffer = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = getLinesAsSingleString(buffer)
                runOnUiThread {
                    binding.webview.loadData(
                        result.toString(),
                        "text/html; utf-8",
                        "utf-8"
                    )
                }
            }.start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLinesAsSingleString(bufferedReader: BufferedReader): String? {
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }

}
