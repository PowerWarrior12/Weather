package com.example.weather.ui.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class CurrentWeatherViewEntity(
    val date : Date,
    val temperature : Double,
    val pressure : Int,
    val humidity : Int,
    var cityId : Int? = null
)
