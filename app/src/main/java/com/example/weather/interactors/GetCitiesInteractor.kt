package com.example.weather.interactors

import com.example.weather.data.interfaces.ICitiesRepository
import com.example.weather.data.repositories.WeatherDBDataSource

private val TAG = GetCitiesInteractor::class.java.simpleName

class GetCitiesInteractor(private val citiesRepository : ICitiesRepository) {
    suspend fun run() = citiesRepository.getCities()
}