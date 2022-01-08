package com.example.weather.data.interfaces

import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface for working with cities
*/
interface ICitiesRepository {

    suspend fun getCities() : Flow<List<CityViewEntity>>
    suspend fun saveCities(cities: List<CityViewEntity>)
    suspend fun updateCity(city : CityViewEntity)
    suspend fun getCurrentCity() : CityViewEntity?
    suspend fun getCity(id : Int) : CityViewEntity?
}