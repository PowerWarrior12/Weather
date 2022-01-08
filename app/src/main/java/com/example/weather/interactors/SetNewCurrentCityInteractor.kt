package com.example.weather.interactors

import android.util.Log
import com.example.weather.data.interfaces.ICitiesRepository
import com.example.weather.ui.entities.CityViewEntity

private val TAG = SetNewCurrentCityInteractor::class.simpleName

class SetNewCurrentCityInteractor(
    private val citiesRepository : ICitiesRepository
) {
    suspend fun run(city : CityViewEntity) {
        val currentCity = citiesRepository.getCurrentCity()
        currentCity?.apply {
            isCurrent = false
        }?.let { citiesRepository.updateCity(it) }
        citiesRepository.updateCity(city.apply {
            isCurrent = true
        })
    }
}