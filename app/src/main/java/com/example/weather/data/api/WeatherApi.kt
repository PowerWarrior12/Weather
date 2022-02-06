package com.example.weather.data.api

import com.example.weather.common.Configurations
import com.example.weather.data.api.entities.CurrentWeatherResponseEntity
import com.example.weather.data.api.entities.WeatherResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/onecall?exclude=current,hourly,minutely,alerts&appid=${Configurations.apiKey}")
    suspend fun loadWeatherByCityCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<WeatherResponseEntity>

    @GET("/data/2.5/onecall?exclude=daily,hourly,minutely,alerts&appid=${Configurations.apiKey}")
    suspend fun loadCurrentWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Response<CurrentWeatherResponseEntity>
}