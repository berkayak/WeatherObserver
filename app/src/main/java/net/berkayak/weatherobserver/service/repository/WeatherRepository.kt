package net.berkayak.weatherobserver.service.repository

import android.content.Context
import androidx.lifecycle.LiveData
import net.berkayak.weatherobserver.service.model.InstantWeatherDAO
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.view.callback.ViewModelCallback

class WeatherRepository {
    private var dao: InstantWeatherDAO
    private var list: LiveData<List<InstantWeatherDBO>>

    constructor(con: Context){
        dao = WeatherDatabase.getInstance(con)!!.InstantWeather()
        list = dao.getAll()
    }

    fun insert(w: InstantWeatherDBO, callback: ViewModelCallback?){
        Thread{
            var id = dao.insert(w)
            callback?.onComplete(id)
        }.start()
    }

    fun update(w: InstantWeatherDBO){
        Thread{
            dao.update(w)
        }.start()
    }

    fun delete(w: InstantWeatherDBO){
        Thread{
            dao.delete(w)
        }.start()
    }

    fun getAll(): LiveData<List<InstantWeatherDBO>>{
        return list
    }
}