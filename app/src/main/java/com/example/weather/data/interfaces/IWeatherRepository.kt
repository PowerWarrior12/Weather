package com.example.weather.data.interfaces

import com.example.weather.ui.entities.CurrentWeatherViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for working with weather
 */
interface IWeatherRepository {
    suspend fun loadRemoteWeather(lat : Double, lon : Double) : List<WeatherViewEntity>
    suspend fun loadLocalWeather(cityId : Int) : Flow<List<WeatherViewEntity>>
    suspend fun addWeather(weather : List<WeatherViewEntity>)
    suspend fun updateWeather(weather : List<WeatherViewEntity>) : Int
    suspend fun loadRemoteCurrentWeather(lat : Double, lon : Double) : CurrentWeatherViewEntity
}