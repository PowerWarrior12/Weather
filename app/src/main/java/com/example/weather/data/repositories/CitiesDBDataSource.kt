package com.example.weather.data.repositories

import android.annotation.SuppressLint
import com.example.weather.data.db.WeatherDatabase
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.db.entities.CityEntity
import com.example.weather.data.interfaces.IEntityMapper
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val TAG = CitiesDBDataSource::class.java.simpleName

class CitiesDBDataSource(
    private val cityDB : WeatherDatabase,
    private val mapperTo : IEntityMapper<CityEntity, CityViewEntity>,
    private val mapperOf : IEntityMapper<CityViewEntity, CityEntity>,
) : ICitiesLocalDataSource {
    @SuppressLint("Range")
    override suspend fun loadCities(): Flow<List<CityViewEntity>> {
        return cityDB.cityDao().getCities().map { list ->
            list.map { cityEntity ->
                mapperTo.mapEntity(cityEntity)
            }
        }
    }

    override suspend fun saveCities(cities: List<CityViewEntity>) {
        cityDB.cityDao().addCities(cities.map{ cityViewEntity ->
            mapperOf.mapEntity(cityViewEntity)
        })
    }

    override suspend fun updateCity(city: CityViewEntity) {
        val newCity = mapperOf.mapEntity(city)
        cityDB.cityDao().updateCity(newCity)
    }

    override suspend fun getCurrentCity(): CityViewEntity? {
        val city = cityDB.cityDao().getCurrentCity(true)
        if (city != null){
            return mapperTo.mapEntity(city)
        }
        return null
    }

    override suspend fun getCity(id: Int): CityViewEntity? {
        val city = cityDB.cityDao().getCity(id)
        if (city != null){
            return mapperTo.mapEntity(city)
        }
        return null
    }
}