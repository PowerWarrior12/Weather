package com.example.weather.data.api.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponseEntity(
    @SerializedName("daily") val dayWeather: List<DayWeather>
){
    data class Temperature(
        val day : Double,
        val min : Double,
        val max : Double,
        val night : Double
    )
    data class DayWeather(
        @SerializedName("dt") val date : Int,
        @SerializedName("temp") val dayTemperature : Temperature,
        @SerializedName("pressure") val pressure : Int,
        @SerializedName("humidity") val humidity : Int,
    )
}
