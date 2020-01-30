package net.berkayak.weatherobserver.service.model


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("type")
    val type: Int,
    @SerializedName("Id")
    val id: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int
)