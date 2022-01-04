package com.example.weather.data.repositories

import com.example.weather.data.interfaces.IWeatherLocalDataSource
import com.example.weather.data.interfaces.IWeatherRemoteDataSource
import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.ui.entities.CurrentWeatherViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.flow.Flow

class WeatherRepository(
    private val remoteDataSource: IWeatherRemoteDataSource,
    private val localDataSource: IWeatherLocalDataSource
) : IWeatherRepository {
    override suspend fun loadRemoteWeather(lat : Double, lon : Double): List<WeatherViewEntity> {
        return remoteDataSource.loadWeather(lat, lon)
    }

    override suspend fun loadLocalWeather(cityId: Int): Flow<List<WeatherViewEntity>> {
        return localDataSource.getWeather(cityId)
    }

    override suspend fun addWeather(weather: List<WeatherViewEntity>) {
        localDataSource.addWeather(weather)
    }

    override suspend fun updateWeather(weather: List<WeatherViewEntity>): Int {
        weather[0].cityId?.let { localDataSource.deleteWeather(cityId = it) }
        localDataSource.addWeather(weather)
        return 1
    }

    override suspend fun loadRemoteCurrentWeather(
        lat: Double,
        lon: Double
    ): CurrentWeatherViewEntity {
        return remoteDataSource.loadCurrentWeather(lat, lon)
    }
}