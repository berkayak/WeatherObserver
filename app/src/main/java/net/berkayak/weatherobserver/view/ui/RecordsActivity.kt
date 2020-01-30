package net.berkayak.weatherobserver.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.berkayak.weatherobserver.R
import net.berkayak.weatherobserver.service.model.InstantWeather
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.utilities.Const
import net.berkayak.weatherobserver.view.adapter.WeatherAdapter
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel

class RecordsActivity : AppCompatActivity() {

    lateinit var weathersRV : RecyclerView
    lateinit var weatherAdapter: WeatherAdapter
    lateinit var weatherVM : InstantWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)
        init()
    }

    fun init(){
        weathersRV = findViewById(R.id.records_RV)
        weatherVM = ViewModelProviders.of(this).get(InstantWeatherViewModel::class.java)
        weatherVM.getAll().observe(this, weatherObserver)
    }

    var weatherObserver = Observer<List<InstantWeatherDBO>> {
        if (::weatherAdapter.isInitialized){
            weatherAdapter.updateDataSet(it)
        } else {
            weatherAdapter = WeatherAdapter(baseContext, it)
            weatherAdapter.notifier = notifier
            weathersRV.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
            weathersRV.adapter = weatherAdapter

        }
    }

    private var notifier = object : WeatherAdapter.WeatherListListener {
        override fun onClick(item: InstantWeatherDBO) {
            var detailIntent = Intent(this@RecordsActivity, WeatherDetailActivity::class.java)
            detailIntent.putExtra(Const.WEATHER_OBJECT, item)
            startActivity(detailIntent)
        }
    }
}
