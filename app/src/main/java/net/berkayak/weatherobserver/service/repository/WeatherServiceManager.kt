package net.berkayak.weatherobserver.service.repository

import retrofit2.GsonConverterFactory
import retrofit2.Retrofit

class WeatherServiceManager {

    companion object{
        private var instance: IWeatherService? = null

        fun getInstance(): IWeatherService? {
            if(instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl(IWeatherService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IWeatherService::class.java)
            }
            return instance
        }

        fun getPhotoJPEG(str: String): String{
            return "http://openweathermap.org/img/w/${str}.png"
        }
    }
}