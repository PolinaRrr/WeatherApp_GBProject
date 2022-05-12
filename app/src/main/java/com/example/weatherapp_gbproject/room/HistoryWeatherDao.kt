package com.example.weatherapp_gbproject.room

import androidx.room.*

@Dao
interface HistoryWeatherDao {

    @Query(
        "INSERT INTO history (date,id_city,temperature,feels_like,condition,wind_speed,wind_dir,pressure_mm) VALUES (:date,:id_city,:temperature,:feels_like,:condition,:wind_speed,:wind_dir,:pressure_mm)"
    )
    fun insert(
        date: String,
        id_city: Long,
        temperature: Int,
        feels_like: Int,
        condition: String,
        wind_speed: Double,
        wind_dir: String,
        pressure_mm: Int
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: HistoryWeatherRequest)

    @Delete
    fun delete(entity: HistoryWeatherRequest)

    @Update
    fun update(entity: HistoryWeatherRequest)

    @Query("SELECT * FROM history")
    fun getInfo(): List<HistoryWeatherRequest>

    @Query("SELECT * FROM history WHERE id_city=:id_city")
    fun getHistoryForCity(id_city: Long): List<HistoryWeatherRequest>

}