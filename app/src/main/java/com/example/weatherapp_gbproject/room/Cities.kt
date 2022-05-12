package com.example.weatherapp_gbproject.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cities")
@Parcelize
data class Cities(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val locality: String,
    val lat: Double,
    val lon: Double,
    val isRussian:Boolean
) : Parcelable

