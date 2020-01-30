package net.berkayak.weatherobserver.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.berkayak.weatherobserver.R
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.viewmodel.InstantWeatherViewModel
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.util.Log
import androidx.core.app.ActivityCompat
import net.berkayak.weatherobserver.view.callback.ViewModelCallback


class MainActivity : AppCompatActivity() {
    lateinit var saveBtn: Button
    lateinit var cityRB: RadioButton
    lateinit var locationRB: RadioButton
    lateinit var cityET: EditText

    lateinit var weatherVM : InstantWeatherViewModel

    private val REQ_CODE_ACCESS_COARSE_LOCATION = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE_ACCESS_COARSE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                doViaGPS()
            else{
                Toast.makeText(baseContext, R.string.need_permission, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun init(){
        saveBtn = findViewById(R.id.save_Btn)
        cityRB = findViewById(R.id.city_name_RB)
        locationRB = findViewById(R.id.gps_location_RB)
        cityET = findViewById(R.id.city_name_ET)

        weatherVM = ViewModelProviders.of(this).get(InstantWeatherViewModel::class.java)
        weatherVM.getAll().observe(this, weatherObserver)
    }

    private var weatherObserver = Observer<List<InstantWeatherDBO>> {

    }

    private var saveListener = View.OnClickListener {
        if (!cityRB.isChecked && !locationRB.isChecked){ //error: return
            Toast.makeText(baseContext, R.string.chekc_any, Toast.LENGTH_LONG).show()
            return@OnClickListener
        } else if(cityRB.isChecked && cityET.text.toString().isNullOrEmpty()){ //error: return
            Toast.makeText(baseContext, R.string.empty_fields, Toast.LENGTH_LONG).show()
            return@OnClickListener
        } else if(locationRB.isChecked){ //location radio checked
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQ_CODE_ACCESS_COARSE_LOCATION)
            } else{
                doViaGPS()
            }
        } else if(cityRB.isChecked  && !cityET.text.toString().isNullOrEmpty()){
            doViaCity(cityET.text.toString())
        }
    }

    private var insertCallback = object : ViewModelCallback {
        override fun onComplete(id: Long) {
            var lastRecord = weatherVM.getAll().value?.find { t -> t.id.toLong() == id }
            if (lastRecord != null)
                Log.i("EFECTURA", lastRecord.temp.toString())
            else
                Log.d("EFECTURA", "null object reference")
        }
    }

    @SuppressLint("MissingPermission")
    private fun doViaGPS(){
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(baseContext, R.string.enbale_gps, Toast.LENGTH_LONG).show()
            val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,
            object : LocationListener {
                override fun onLocationChanged(p0: Location?) {
                    weatherVM.addViaNetwork(p0?.latitude!!, p0?.longitude!!, insertCallback, onComplete = {
                        if (!it)
                            Toast.makeText(baseContext, R.string.error, Toast.LENGTH_LONG).show()
                    })
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
        weatherVM.addViaNetwork(cityName, insertCallback, onComplete = {
            if (!it)
                Toast.makeText(baseContext, R.string.error, Toast.LENGTH_LONG).show()
        })
    }






}
