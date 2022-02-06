package com.example.weather.data.mappers

import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.WeatherViewEntity

class WeatherEntityMapper : IEntityMapper<WeatherViewEntity, WeatherEntity> {
    override fun mapEntity(entity: WeatherViewEntity): WeatherEntity {
        return WeatherEntity(
            date = entity.date,
            minTemperature = entity.minTemperature,
            maxTemperature = entity.maxTemperature,
            humidity = entity.humidity,
            pressure = entity.pressure,
            cityId = entity.cityId
        )
    }
}