package com.example.weather.ui.entities

import java.util.*

data class CurrentWeatherViewEntity(
    val date : Date,
    val temperature : Double,
    val pressure : Int,
    val humidity : Int,
    var cityId : Int? = null
)
