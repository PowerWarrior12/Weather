package com.example.weather.data.db

import androidx.room.*
import com.example.weather.data.db.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CityDao {

    @Insert
    abstract suspend fun addCities(cities : List<CityEntity>)

    @Transaction
    @Query("SELECT * FROM city")
    abstract fun getCities() : Flow<List<CityEntity>>

    @Update
    abstract suspend fun updateCity(city : CityEntity)

    @Transaction
    @Query("SELECT * FROM city WHERE isCurrent = :isCurrent")
    abstract fun getCurrentCity(isCurrent : Boolean) : CityEntity?

    @Transaction
    @Query("SELECT * FROM city WHERE id = :id")
    abstract fun getCity(id : Int) : CityEntity?
}