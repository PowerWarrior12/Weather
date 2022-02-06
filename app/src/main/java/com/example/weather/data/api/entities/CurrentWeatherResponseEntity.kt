package com.example.weather.data.api.entities

import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponseEntity(
    @SerializedName("current") val currentWeather : CurrentWeather
)

data class CurrentWeather(
    @SerializedName("dt") val date : Int,
    @SerializedName("temp") val temperature : Double,
    @SerializedName("pressure") val pressure : Int,
    @SerializedName("humidity") val humidity : Int,
)
