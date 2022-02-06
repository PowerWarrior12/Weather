package com.example.weather.data.mappers

import com.example.weather.data.api.entities.CurrentWeatherResponseEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.CurrentWeatherViewEntity
import java.util.*

class ResponseViewWeatherMapper : IEntityMapper<CurrentWeatherResponseEntity, CurrentWeatherViewEntity> {
    override fun mapEntity(entity: CurrentWeatherResponseEntity): CurrentWeatherViewEntity {
        return CurrentWeatherViewEntity(
            date = Date(entity.currentWeather.date.toLong() * 1000),
            temperature = entity.currentWeather.temperature,
            pressure = entity.currentWeather.pressure,
            humidity = entity.currentWeather.humidity
        )
    }
}