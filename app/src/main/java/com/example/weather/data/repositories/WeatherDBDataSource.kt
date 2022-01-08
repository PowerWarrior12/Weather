package com.example.weather.data.repositories

import com.example.weather.data.db.WeatherDatabase
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.data.interfaces.IWeatherLocalDataSource
import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val TAG = WeatherDBDataSource::class.java.simpleName

class WeatherDBDataSource(
    private val db : WeatherDatabase,
    private val mapperTo : IEntityMapper<WeatherEntity, WeatherViewEntity>,
    private val mapperFrom : IEntityMapper<WeatherViewEntity, WeatherEntity>
) : IWeatherLocalDataSource {
    override suspend fun getWeather(cityId: Int): Flow<List<WeatherViewEntity>> {
        return db.weatherDao().getWeather(cityId).map { list ->
            list.map { weatherEntity ->
                mapperTo.mapEntity(weatherEntity)
            }
        }
    }

    override suspend fun addWeather(weather: List<WeatherViewEntity>) {
        db.weatherDao().addWeather(weather = weather.map { weatherViewEntity ->
            mapperFrom.mapEntity(weatherViewEntity)
        })
    }

    override suspend fun updateWeather(weather: List<WeatherViewEntity>): Int {
        return db.weatherDao().updateWeather(weather = weather.map { weatherViewEntity ->
            mapperFrom.mapEntity(weatherViewEntity)
        })
    }

    override suspend fun deleteWeather(cityId: Int) {
        db.weatherDao().deleteWeather(cityId)
    }
}