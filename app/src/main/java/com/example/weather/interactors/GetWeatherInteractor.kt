package com.example.weather.interactors

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.weather.common.NetworkHelper
import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private val TAG = GetWeatherInteractor::class.simpleName

class GetWeatherInteractor(
    private val weatherRepository: IWeatherRepository
) {
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun run(city : CityViewEntity, pScope : CoroutineScope) : Flow<List<WeatherViewEntity>> {
        if (NetworkHelper.isNetworkAvailable()){
            pScope.launch{
                val weather = weatherRepository.loadRemoteWeather(city.coord.lat, city.coord.lon)
                if (weather.isNotEmpty()){
                    weatherRepository.updateWeather(weather.map{ weatherViewEntity ->
                        weatherViewEntity.apply {
                            this.cityId = city.id
                        }
                    })
                }
            }
        }
        val weather = weatherRepository.loadLocalWeather(cityId = city.id)
        Log.d(TAG, "BD loaded in ${Thread.currentThread().name}")
        return weather
    }
}