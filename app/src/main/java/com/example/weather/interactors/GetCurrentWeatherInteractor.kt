package com.example.weather.interactors

import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.CurrentWeatherViewEntity

private val TAG = GetCurrentWeatherInteractor::class.java.simpleName

class GetCurrentWeatherInteractor(
    private val weatherRepository : IWeatherRepository
) {
    suspend fun run(city : CityViewEntity) : CurrentWeatherViewEntity{
        return weatherRepository.loadRemoteCurrentWeather(city.coord.lat, city.coord.lon)
    }
}