package com.example.weather.interactors

import com.example.weather.data.interfaces.ICitiesRepository

private val TAG = GetCurrentCityInteractor::class.java.simpleName

class GetCurrentCityInteractor(
    private val citiesRepository: ICitiesRepository
) {
    suspend fun run() = citiesRepository.getCurrentCity()
}