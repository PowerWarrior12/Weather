package com.example.weather.data.repositories

import com.example.weather.common.JSONHelper
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.db.entities.CityEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Work with json cities file
*/
class CitiesJSONDataSource(
    private val jsonHelper : JSONHelper
    ) : ICitiesLocalDataSource {
    override suspend fun loadCities(): Flow<List<CityViewEntity>> = flow{
        emit(jsonHelper.importAll())
    }

    override suspend fun saveCities(cities: List<CityViewEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCity(city: CityViewEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentCity(): CityViewEntity? {
        TODO("Not yet implemented")
    }

    override suspend fun getCity(id: Int): CityViewEntity? {
        TODO("Not yet implemented")
    }
}