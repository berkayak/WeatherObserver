package net.berkayak.weatherobserver.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.berkayak.weatherobserver.R
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.widget.*
import net.berkayak.weatherobserver.utilities.Const
import net.berkayak.weatherobserver.view.callback.ViewModelCallback
import net.berkayak.weatherobserver.view.ui.di.ComponentBuilds
import net.berkayak.weatherobserver.view.ui.di.WDViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    lateinit var saveBtn: Button
    lateinit var listBtn: Button
    lateinit var cityRB: RadioButton
    lateinit var locationRB: RadioButton
    lateinit var cityET: EditText
    lateinit var progress: ProgressBar
    var goDetailActivation = false
    private val REQ_CODE_ACCESS_FINE_LOCATION = 401


    private val component by lazy { ComponentBuilds.wdComponent()}

    @Inject
    lateinit var viewModelFactory: WDViewModelFactory

    private val viewModel: InstantWeatherViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(InstantWeatherViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_main)
        init()

    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE_ACCESS_FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                doViaGPS()
            else{
                Toast.makeText(baseContext, R.string.need_permission, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun init(){
        saveBtn = findViewById(R.id.save_Btn)
        listBtn = findViewById(R.id.list_Btn)
        cityRB = findViewById(R.id.city_name_RB)
        locationRB = findViewById(R.id.gps_location_RB)
        cityET = findViewById(R.id.city_name_ET)
        progress = findViewById(R.id.progress_PB)
        saveBtn.setOnClickListener(saveListener)
        listBtn.setOnClickListener(listListener)

        viewModel.getAll().observe(this, weatherObserver)
    }

    private var weatherObserver = Observer<List<InstantWeatherDBO>> {
        if (goDetailActivation){
            var lastRecord = it[it.lastIndex]
            startDetailActivity(lastRecord)
            goDetailActivation = false
        }
    }

    private var saveListener = View.OnClickListener {
        if (!cityRB.isChecked && !locationRB.isChecked){ //error: return
            Toast.makeText(baseContext, R.string.chekc_any, Toast.LENGTH_LONG).show()
            return@OnClickListener
        } else if(cityRB.isChecked && cityET.text.toString().isNullOrEmpty()){ //error: return
            Toast.makeText(baseContext, R.string.empty_fields, Toast.LENGTH_LONG).show()
            return@OnClickListener
        } else if(locationRB.isChecked){ //location radio checked
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_CODE_ACCESS_FINE_LOCATION)
            } else{
                doViaGPS()
            }
        } else if(cityRB.isChecked  && !cityET.text.toString().isNullOrEmpty()){
            doViaCity(cityET.text.toString())
        }
    }

    private var listListener = View.OnClickListener {
        startListActivity()
    }

    private var insertCallback = object : ViewModelCallback {
        override fun onComplete(id: Long) {
            goDetailActivation = true
            runOnUiThread { progress.visibility = View.GONE }
        }
    }

    @SuppressLint("MissingPermission")
    private fun doViaGPS(){
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(baseContext, R.string.enbale_gps, Toast.LENGTH_LONG).show()
        }

        progress.visibility = View.VISIBLE

        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, object : LocationListener {
                override fun onLocationChanged(p0: Location?) {
                    viewModel.addViaNetwork(p0?.latitude!!, p0?.longitude!!, insertCallback, onComp)
                    progress.visibility = View.GONE
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }
            }, null)
    }

    private fun doViaCity(cityName: String){
        progress.visibility = View.VISIBLE
        viewModel.addViaNetwork(cityName, insertCallback, onComplete = onComp)
    }

    private fun startDetailActivity(lastRecord: InstantWeatherDBO){
        var detailIntent = Intent(this@MainActivity, WeatherDetailActivity::class.java)
        detailIntent.putExtra(Const.WEATHER_OBJECT, lastRecord)
        startActivity(detailIntent)
    }

    private fun startListActivity(){
        var recordsIntent = Intent(this@MainActivity, RecordsActivity::class.java)
        startActivity(recordsIntent)
    }


    private val onComp: (Boolean) -> Unit = {
        if (!it)
            Toast.makeText(baseContext, R.string.error, Toast.LENGTH_LONG).show()
        else
            Toast.makeText(baseContext, R.string.completed, Toast.LENGTH_LONG).show()
    }

}


