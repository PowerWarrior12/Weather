package com.example.weather.interactors

import com.example.weather.data.interfaces.ICitiesRepository

private val TAG = GetCityInteractor::class.java.simpleName

class GetCityInteractor(
    private val cityRepository: ICitiesRepository
) {
    suspend fun run (id : Int) = cityRepository.getCity(id)
}