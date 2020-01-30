package net.berkayak.weatherobserver.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import net.berkayak.weatherobserver.R
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.service.repository.WeatherServiceManager
import net.berkayak.weatherobserver.utilities.Const
import net.berkayak.weatherobserver.utilities.loadViaNetwork
import net.berkayak.weatherobserver.utilities.toDateFormat

class WeatherDetailActivity : AppCompatActivity() {
    lateinit var iconIV: ImageView
    lateinit var locationTV: TextView
    lateinit var tempTV: TextView
    lateinit var coordinatesTV: TextView
    lateinit var insertTimeTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)
        init()

    }

    fun init(){
        iconIV = findViewById(R.id.icon_IV)
        locationTV = findViewById(R.id.location_TV)
        tempTV = findViewById(R.id.temp_TV)
        coordinatesTV = findViewById(R.id.coordinates_TV)
        insertTimeTV = findViewById(R.id.inserted_time_TV)

        var obj = intent.getSerializableExtra(Const.WEATHER_OBJECT) as InstantWeatherDBO
        if (obj != null)
            putFields(obj)
    }

    fun putFields(ob: InstantWeatherDBO){
        iconIV.loadViaNetwork(WeatherServiceManager.getPhotoJPEG(ob.Icon))
        locationTV.text = ob.city + ", " + ob.country
        tempTV.text = ob.temp.toInt().toString() + "°C, " + ob.weatherDesc
        coordinatesTV.text = "Lat: ${ob.lat}, Lon: ${ob.lon}"
        insertTimeTV.text = ob.InsertTime.toDateFormat(null)
    }
}
