package com.example.weather.data.repositories

import com.example.weather.common.JSONHelper
import com.example.weather.data.interfaces.ICitiesLoadDataSource
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.interactors.GetCurrentCityInteractor
import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private val TAG = CitiesJSONDataSource::class.java.simpleName

class CitiesJSONDataSource(
    private val jsonHelper : JSONHelper
    ) : ICitiesLoadDataSource {
    override suspend fun loadCities(): Flow<List<CityViewEntity>> = flow{
        emit(jsonHelper.importAll())
    }
}