package net.berkayak.weatherobserver.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.berkayak.weatherobserver.R
import net.berkayak.weatherobserver.service.model.InstantWeatherDBO
import net.berkayak.weatherobserver.service.repository.WeatherServiceManager
import net.berkayak.weatherobserver.utilities.loadViaNetwork
import net.berkayak.weatherobserver.utilities.toDateFormat


class WeatherAdapter(val context: Context, private var list: List<InstantWeatherDBO>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    lateinit var notifier: WeatherListListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    fun updateDataSet(l: List<InstantWeatherDBO>){
        this.list = l
        notifyDataSetChanged()
    }


    inner class WeatherViewHolder(v: View): RecyclerView.ViewHolder(v){
        var icon: ImageView = v.findViewById(R.id.icon_IV)
        var time: TextView = v.findViewById(R.id.time_TV)
        var recordId: TextView = v.findViewById(R.id.record_id_TV)
        var location: TextView = v.findViewById(R.id.location_TV)
        var temp: TextView = v.findViewById(R.id.temp_TV)

        fun bindItem(item: InstantWeatherDBO){
            //bind fields
            icon.loadViaNetwork(WeatherServiceManager.getPhotoJPEG(item.Icon))
            time.text = item.InsertTime.toDateFormat(null)
            recordId.text = item.Id.toString()
            location.text = item.city + ", " + item.country
            temp.text = item.temp.toInt().toString() + "°C"

            //onClick event
            if (::notifier.isInitialized)
                itemView.setOnClickListener {
                    notifier.onClick(item)
                }
        }
    }

    interface WeatherListListener{
        fun onClick(item: InstantWeatherDBO)
    }
}