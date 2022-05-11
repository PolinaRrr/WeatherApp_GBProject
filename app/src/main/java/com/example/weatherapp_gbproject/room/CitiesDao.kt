package com.example.weatherapp_gbproject.room

import androidx.room.*

@Dao
interface CitiesDao {
    @Query("INSERT INTO cities (locality,lat,lon,isRussian) VALUES (:locality,:lat,:lon,:isRussian)")
    fun insert(locality: String,
               lat: Double,
               lon: Double,
               isRussian:Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: Cities)

    @Update
    fun update(entity: Cities)

    @Delete
    fun delete(entity: Cities)

    @Query("SELECT locality FROM cities WHERE id=:id_city")
    fun getNameCity(id_city: Long):String
}