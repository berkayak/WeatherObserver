package net.berkayak.weatherobserver.utilities

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

fun ImageView.loadViaNetwork(url: String){
    Picasso.get()?.
        load(url)?.
        error(android.R.drawable.presence_busy)?.
        placeholder(android.R.drawable.presence_busy)?.
        into(this)
}

fun Long.toDateFormat(format: String?): String{
    return try{
        if(format != null){
            SimpleDateFormat(format).format(this)
        } else {
            SimpleDateFormat("dd/MM/yyyy HH:mm").format(this)
        }
    } catch (e: Exception){
        "01/01/1900 00:00"
    }
}