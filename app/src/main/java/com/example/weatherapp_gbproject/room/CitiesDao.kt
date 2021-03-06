package com.example.weatherapp_gbproject.room

import androidx.room.*
import com.example.weatherapp_gbproject.repository.dto.CoordinatesDTO

@Dao
interface CitiesDao {
    @Query("INSERT INTO cities (locality,lat,lon,isRussian) VALUES (:locality,:lat,:lon,:isRussian)")
    fun insert(
        locality: String,
        lat: Double,
        lon: Double,
        isRussian: Boolean
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Cities)

    @Update
    fun update(entity: Cities)

    @Delete
    fun delete(entity: Cities)

    @Query("SELECT locality FROM cities WHERE id=:id_city")
    fun getNameCity(id_city: Long): String

    @Query("SELECT lat,lon FROM cities WHERE locality=:locality")
    fun getCoordinatesCity(locality: String): CoordinatesDTO

    @Query("SELECT id FROM cities WHERE locality=:locality")
    fun getIdCity(locality: String): Long

    @Query("SELECT locality FROM cities WHERE isRussian=:isRussian")
    fun getAllLocaleCities(isRussian: Boolean): List<String>

    @Query("SELECT * FROM cities")
    fun getAllCities(): List<Cities>


}