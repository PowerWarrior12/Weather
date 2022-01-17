package com.example.weather.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.work.*
import com.example.weather.data.interfaces.IWeatherRepository
import com.example.weather.data.repositories.CitiesRepository
import com.example.weather.interactors.*
import com.example.weather.ui.di.DependenciesProvider
import com.example.weather.ui.services.CurrentWeatherWorker
import com.example.weather.ui.views.CircleDiagramView
import java.util.concurrent.TimeUnit

private const val WorkTag = "CurrentWeatherTag"
private val TAG = WeatherApplication::class.java.simpleName

class WeatherApplication : Application() {

    fun startCurrentWeatherService(){
        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val currentWeatherWorkRequest = PeriodicWorkRequest.Builder(CurrentWeatherWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag(WorkTag)
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(WorkTag, ExistingPeriodicWorkPolicy.REPLACE, currentWeatherWorkRequest)
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