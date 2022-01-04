package com.example.weather.interactors

import com.example.weather.data.interfaces.ICitiesRepository

class GetCurrentCityInteractor(
    private val citiesRepository: ICitiesRepository
) {
    suspend fun run() = citiesRepository.getCurrentCity()
}