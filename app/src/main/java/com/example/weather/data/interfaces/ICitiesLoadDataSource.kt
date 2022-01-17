package com.example.weather.data.interfaces

import com.example.weather.ui.entities.CityViewEntity
import kotlinx.coroutines.flow.Flow

interface ICitiesLoadDataSource {
    suspend fun loadCities() : Flow<List<CityViewEntity>>
}