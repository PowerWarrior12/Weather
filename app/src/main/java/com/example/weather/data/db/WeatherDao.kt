package com.example.weather.data.db

import androidx.room.*
import com.example.weather.data.db.entities.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Transaction
    @Query("SELECT * FROM weather WHERE cityId=:cityId")
    fun getWeather(cityId : Int) : Flow<List<WeatherEntity>>

    @Update
    suspend fun updateWeather(weather : List<WeatherEntity>) : Int

    @Insert
    suspend fun addWeather(weather: List<WeatherEntity>)

    @Transaction
    @Query("DELETE FROM weather WHERE cityId=:cityId")
    suspend fun deleteWeather(cityId: Int)
}