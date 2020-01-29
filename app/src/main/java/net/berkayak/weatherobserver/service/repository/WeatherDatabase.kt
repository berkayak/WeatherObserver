package net.berkayak.weatherobserver.service.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.berkayak.weatherobserver.service.model.InstantWeatherDAO
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO

@Database(entities = [InstantWeatherDBO::class], version = 1, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase(){
    abstract fun InstantWeather(): InstantWeatherDAO

    companion object{
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase? {
            if (instance == null){
                synchronized(WeatherDatabase::class){
                    instance = Room.databaseBuilder(context, WeatherDatabase::class.java, "WeatherDatabase").build()
                }
            }
            return instance
        }
    }
}