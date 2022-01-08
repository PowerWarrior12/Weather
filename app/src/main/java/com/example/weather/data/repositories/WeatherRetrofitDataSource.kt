package com.example.weather.data.repositories

import com.example.weather.data.api.WeatherApi
import com.example.weather.data.api.entities.CurrentWeatherResponseEntity
import com.example.weather.data.api.entities.WeatherResponseEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.data.interfaces.IWeatherRemoteDataSource
import com.example.weather.ui.entities.CurrentWeatherViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import java.lang.Exception
import java.util.*

private val TAG = WeatherRetrofitDataSource::class.java.simpleName

class WeatherRetrofitDataSource(
    private val service : WeatherApi,
    private val weatherMapper : IEntityMapper<WeatherResponseEntity, List<WeatherViewEntity>>,
    private val currentWeatherMapper : IEntityMapper<CurrentWeatherResponseEntity, CurrentWeatherViewEntity>,
) : IWeatherRemoteDataSource{

    override suspend fun loadWeather(lat : Double, lon : Double): List<WeatherViewEntity> {
        try{
            val response = service.loadWeatherByCityCoordinates(lat, lon)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    return weatherMapper.mapEntity(body)
                }
            }
            return emptyList()
        }
        catch (exception : Exception){
            exception.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun loadCurrentWeather(lat: Double, lon: Double): CurrentWeatherViewEntity {
        try{
            val response = service.loadCurrentWeatherByCoordinates(lat, lon)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    return currentWeatherMapper.mapEntity(body)
                }
            }
            return CurrentWeatherViewEntity(Date(0), 0.0, 0, 0)
        }
        catch (exception : Exception){
            exception.printStackTrace()
            return CurrentWeatherViewEntity(Date(0), 0.0, 0, 0)
        }

    }
}