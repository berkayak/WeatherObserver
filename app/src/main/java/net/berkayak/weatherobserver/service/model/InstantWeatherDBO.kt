package net.berkayak.weatherobserver.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "WEATHER")
data class InstantWeatherDBO(@PrimaryKey(autoGenerate = true) val Id: Long,
                             val city: String,
                             val country: String,
                             val temp: Double,
                             val weatherDesc: String,
                             val Icon: String,
                             val lat: Double,
                             val lon: Double,
                             val InsertTime: Long): Serializable