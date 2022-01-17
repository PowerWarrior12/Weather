package com.example.weather.data.repositories

import android.util.Log
import com.example.weather.data.interfaces.ICitiesLoadDataSource
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.interfaces.ICitiesRepository
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow

private val TAG = CitiesRepository::class.java.simpleName

class CitiesRepository(
    private val citiesDataSource : ICitiesLocalDataSource,
    private val citiesLoadDataSource : ICitiesLoadDataSource
) : ICitiesRepository {

    override suspend fun getCities(): Flow<List<CityViewEntity>> {
        return citiesLoadDataSource.loadCities()
    }

    override suspend fun saveCities(cities: List<CityViewEntity>) {
        citiesDataSource.saveCities(cities)
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