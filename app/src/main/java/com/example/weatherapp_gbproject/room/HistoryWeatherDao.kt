package com.example.weatherapp_gbproject.room

import androidx.room.*
import com.example.weatherapp_gbproject.repository.dto.HistoryDTO

@Dao
interface HistoryWeatherDao {

    @Query(
        "INSERT INTO history (id_city,temperature,feels_like,condition,icon,wind_speed,wind_dir,pressure_mm) " +
                "VALUES (:id_city,:temperature,:feels_like,:condition,:icon,:wind_speed,:wind_dir,:pressure_mm)"
    )
    fun insert(
        id_city: Long,
        temperature: Int,
        feels_like: Int,
        condition: String,
        icon: String,
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

    @Query(
        "SELECT date, c.locality AS city_name, temperature, feels_like, condition, icon, wind_speed, wind_dir, pressure_mm " +
                "FROM history AS h " +
                "LEFT JOIN cities AS c ON h.id_city=c.id " +
                "LIMIT 10"
    )
    fun getHistory(): List<HistoryDTO>

    @Query("SELECT id_city FROM history")
    fun getCityId(): Long

    @Query("SELECT temperature FROM history")
    fun getTemperature(): Int


    @Query("SELECT * FROM history WHERE id_city=:id_city")
    fun getHistoryForCity(id_city: Long): List<HistoryWeatherRequest>

}