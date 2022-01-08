package com.example.weather.data.repositories

import android.util.Log
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.interfaces.ICitiesRepository
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow

private val TAG = CitiesRepository::class.java.simpleName

class CitiesRepository(
    private val citiesDataSource : ICitiesLocalDataSource
) : ICitiesRepository {

    override suspend fun getCities(): Flow<List<CityViewEntity>> {
        return citiesDataSource.loadCities()
    }

    override suspend fun saveCities(cities: List<CityViewEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCity(city: CityViewEntity) {
        citiesDataSource.updateCity(city)
    }

    override suspend fun getCurrentCity(): CityViewEntity? {
        return citiesDataSource.getCurrentCity()
    }

    override suspend fun getCity(id: Int): CityViewEntity? {
        return citiesDataSource.getCity(id)
    }
}