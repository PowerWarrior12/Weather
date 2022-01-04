package com.example.weather.data.mappers

import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.WeatherViewEntity
import kotlin.math.max

class WeatherViewEntityMapper : IEntityMapper<WeatherEntity, WeatherViewEntity> {
    override fun mapEntity(entity: WeatherEntity): WeatherViewEntity {
        return WeatherViewEntity(
            date = entity.date,
            minTemperature = entity.minTemperature,
            maxTemperature = entity.maxTemperature,
            pressure = entity.pressure,
            humidity = entity.humidity,
            cityId = entity.cityId
        )
    }
}