package com.example.weather.data.interfaces

import com.example.weather.data.db.entities.CityEntity
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interface to work with cities in a local data source
*/
interface ICitiesLocalDataSource {
    suspend fun loadCities() : Flow<List<CityViewEntity>>
    suspend fun saveCities(cities: List<CityViewEntity>)
    suspend fun updateCity(city : CityViewEntity)
    suspend fun getCurrentCity() : CityViewEntity?
    suspend fun getCity(id : Int) : CityViewEntity?
}