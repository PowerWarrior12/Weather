package com.example.weather.data.interfaces

import com.example.weather.ui.entities.CurrentWeatherViewEntity
import com.example.weather.ui.entities.WeatherViewEntity

/**
 * Interface for access to remote weather data source
*/
interface IWeatherRemoteDataSource {
    suspend fun loadWeather(lat : Double, lon : Double) : List<WeatherViewEntity>
    suspend fun loadCurrentWeather(lat: Double, lon: Double) : CurrentWeatherViewEntity
}