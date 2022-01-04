package com.example.weather.data.mappers

import com.example.weather.data.api.entities.WeatherResponseEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.WeatherViewEntity
import java.util.*
//date = DateFormat.format("dd LL yyyy",date).toString()
/** Converting the result of a request to a remote source into a weather entity */
class WeatherResponseEntityMapper : IEntityMapper<WeatherResponseEntity, List<WeatherViewEntity>> {
    override fun mapEntity(entity: WeatherResponseEntity): List<WeatherViewEntity> {
        return mutableListOf<WeatherViewEntity>().apply{
            addAll(entity.dayWeather.map { response ->
                WeatherViewEntity(
                    date = Date(response.date.toLong() * 1000),
                    minTemperature = response.dayTemperature.min,
                    maxTemperature = response.dayTemperature.max,
                    pressure = response.pressure,
                    humidity = response.humidity
                )
            })
        }
    }
}