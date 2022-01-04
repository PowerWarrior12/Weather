package com.example.weather.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.data.repositories.CitiesRepository
import com.example.weather.interactors.*
import com.example.weather.ui.di.DependenciesProvider
import com.example.weather.ui.services.CurrentWeatherWorker
import java.util.concurrent.TimeUnit

class WeatherApplication : Application() {

    private val citiesRepository : CitiesRepository
        get() = DependenciesProvider.getCitiesRepository(this)

    private val weatherRepository : IWeatherRepository
        get() = DependenciesProvider.getWeatherRepository(this)

    val getCitiesInteractor : GetCitiesInteractor
        get() = GetCitiesInteractor(citiesRepository)

    val getCurrentCityInteractor : GetCurrentCityInteractor
        get() = GetCurrentCityInteractor(citiesRepository)

    val getWeatherInteractor : GetWeatherInteractor
        get() = GetWeatherInteractor(weatherRepository)

    val getSetNewCurrentCityInteractor : SetNewCurrentCityInteractor
        get() = SetNewCurrentCityInteractor(citiesRepository)

    val getCurrentWeatherInteractor : GetCurrentWeatherInteractor
        get() = GetCurrentWeatherInteractor(weatherRepository)

    fun startCurrentWeatherService(){
        val myWorkRequest = PeriodicWorkRequest.Builder(CurrentWeatherWorker::class.java, 30, TimeUnit.MINUTES)
            .build()
        WorkManager
            .getInstance(this)
            .enqueue(myWorkRequest)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        startCurrentWeatherService()
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }
}