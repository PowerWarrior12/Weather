package com.example.weather.ui.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class WeatherViewEntity(
    val date : Date,
    val minTemperature : Double,
    val maxTemperature : Double,
    val pressure : Int,
    val humidity : Int,
    var cityId : Int? = null
) {
}