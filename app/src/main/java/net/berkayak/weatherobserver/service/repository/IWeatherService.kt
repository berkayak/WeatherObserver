package net.berkayak.weatherobserver.service.repository

import net.berkayak.weatherobserver.service.model.InstantWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {

    companion object{
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "9b19d60baa38c692f1bdb4db53e2d0ce"
    }

    @GET("weather")
    fun getInstantWeather(@Query("q")cityName: String,
                          @Query("appid")apiKey : String = API_KEY): Call<InstantWeather>

    @GET("weather")
    fun getInstantWeather(@Query("lat")lat: Double,
                          @Query("lon")lon: Double,
                          @Query("appid")apiKey : String = API_KEY): Call<InstantWeather>

}