package com.example.weather.data.interfaces

import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.flow.Flow
/**
 * Interface for access to local weather data source
*/
interface IWeatherLocalDataSource {
    suspend fun getWeather(cityId : Int) : Flow<List<WeatherViewEntity>>
    suspend fun addWeather(weather : List<WeatherViewEntity>)
    // The returned number describes the number of successfully updated records
    suspend fun updateWeather(weather : List<WeatherViewEntity>) : Int
    suspend fun deleteWeather(cityId: Int)
}