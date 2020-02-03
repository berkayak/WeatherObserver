package net.berkayak.weatherobserver.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.berkayak.weatherobserver.service.model.InstantWeather
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.service.repository.WeatherRepository
import net.berkayak.weatherobserver.service.repository.WeatherServiceManager
import net.berkayak.weatherobserver.view.callback.ViewModelCallback
import net.berkayak.weatherobserver.view.ui.di.CoreApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class InstantWeatherViewModel: ViewModel() {

    private var repo : WeatherRepository = WeatherRepository(CoreApp.coreComponent.context())
    private var list : LiveData<List<InstantWeatherDBO>> = repo.getAll()

    fun insert(w: InstantWeatherDBO, callback: ViewModelCallback?){
        repo.insert(w, callback)
    }

    fun delete(w: InstantWeatherDBO){
        repo.delete(w)
    }

    fun getAll(): LiveData<List<InstantWeatherDBO>> {
        return list
    }

    //for the callback pass to onComplete function
    fun addViaNetwork(city: String, callback: ViewModelCallback?, onComplete: (isSuccess: Boolean) -> Unit){
        WeatherServiceManager.getInstance()!!.getInstantWeather(city).enqueue(DataRetrived(onComplete, callback))
    }

    fun addViaNetwork(lat: Double, lon: Double, callback: ViewModelCallback?, onComplete: (isSuccess: Boolean) -> Unit){
        WeatherServiceManager.getInstance()!!.getInstantWeather(lat, lon).enqueue(DataRetrived(onComplete,callback))
    }


    private fun parseToDBO(obj: InstantWeather): InstantWeatherDBO {
        return InstantWeatherDBO(0,
            obj.name,
            obj.sys.country,
            obj.main.temp - 273.15,
            obj.weather[0].description,
            obj.weather[0].icon,
            obj.coord.lat,
            obj.coord.lon,
            Calendar.getInstance().timeInMillis)
    }



    //generic response
    inner class DataRetrived(private val onComplete: (isSuccess: Boolean) -> Unit,
                             private val callback: ViewModelCallback?) : Callback<InstantWeather> {
        override fun onFailure(call: Call<InstantWeather>, t: Throwable) {
            onComplete(false)
            callback?.onComplete(0)
        }

        override fun onResponse(call: Call<InstantWeather>, response: Response<InstantWeather>) {
            onComplete(true)
            insert(parseToDBO(response.body()!!), callback)
        }

    }


}