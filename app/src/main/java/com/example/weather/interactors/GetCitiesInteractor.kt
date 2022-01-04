package com.example.weather.interactors

import com.example.weather.data.interfaces.ICitiesRepository
import com.example.weather.data.repositories.CitiesRepository

class GetCitiesInteractor(private val citiesRepository : ICitiesRepository) {
    suspend fun run() = citiesRepository.getCities()
}