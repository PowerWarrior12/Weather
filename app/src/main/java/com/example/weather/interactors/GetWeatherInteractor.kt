package com.example.weather.interactors

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weather.common.NetworkHelper
import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val TAG = GetWeatherInteractor::class.simpleName

class GetWeatherInteractor(
    private val weatherRepository: IWeatherRepository
) {
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun run(city : CityViewEntity, pScope : CoroutineScope) : Flow<List<WeatherViewEntity>> {
        if (NetworkHelper.isNetworkAvailable()){
            pScope.launch{
                Log.d(TAG, "Network load in ${Thread.currentThread().name}")
                val weather = weatherRepository.loadRemoteWeather(city.coord.lat, city.coord.lon)
                if (weather.isNotEmpty()){
                    weatherRepository.updateWeather(weather.map{ weatherViewEntity ->
                        weatherViewEntity.apply {
                            this.cityId = city.id
                        }
                    })
                }
                Log.d(TAG, "Network loaded in ${Thread.currentThread().name}")
            }
        }
        val weather = weatherRepository.loadLocalWeather(cityId = city.id)
        Log.d(TAG, "BD load in ${Thread.currentThread().name}")
        return weather
    }
}