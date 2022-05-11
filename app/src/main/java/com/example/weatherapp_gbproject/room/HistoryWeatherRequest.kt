package com.example.weatherapp_gbproject.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "history")
@Parcelize
data class HistoryWeatherRequest(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    var id_city: Long,
    val temperature: Int,
    val feels_like: Int,
    val condition: String,
    val wind_speed: Double,
    val wind_dir: String,
    val pressure_mm: Int
) : Parcelable

