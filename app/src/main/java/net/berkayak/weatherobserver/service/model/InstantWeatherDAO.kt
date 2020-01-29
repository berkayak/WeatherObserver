package net.berkayak.weatherobserver.service.model

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface InstantWeatherDAO {
    @Insert
    fun insert(obj: InstantWeatherDBO): Long

    @Update
    fun update(obj: InstantWeatherDBO)

    @Delete
    fun delete(obj: InstantWeatherDBO)

    @Query("Select * from WEATHER")
    fun getAll(): LiveData<List<InstantWeatherDBO>>
}