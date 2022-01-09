package com.example.weather.ui.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weather.R
import com.example.weather.ui.WeatherApplication
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.CurrentWeatherViewEntity
import kotlinx.coroutines.*

private val TAG = CurrentWeatherWorker::class.simpleName
private const val ChanelId = "WeatherChanel"
private const val ChanelTitle = "Weather Chanel"
private const val NotificationId = 1

class CurrentWeatherWorker(private val appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val getCurrentWeatherInteractor = (appContext as WeatherApplication).getCurrentWeatherInteractor
    private val getCurrentCityInteractor = (appContext as WeatherApplication).getCurrentCityInteractor
    private val notificationManager = appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private var currentCity : CityViewEntity? = null
    private var currentWeather : CurrentWeatherViewEntity? = null
    private lateinit var notification: Notification

    override suspend fun doWork(): Result = coroutineScope {
        update()
        Result.success()
    }

    private suspend fun updateCurrentWeather(){
        currentWeather = currentCity?.let {
            getCurrentWeatherInteractor.run(city = it)
        }
    }

    private fun initNotificationBuilder(): NotificationCompat.Builder {
        Log.d(TAG, "Init Notification builder")
        return NotificationCompat.Builder(appContext, ChanelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(currentCity?.name ?: "NONE")
            .setContentText("Current temperature is ${currentWeather?.temperature ?: "NONE"}")
    }
    private fun initNotification() : Notification {
        val builder = initNotificationBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = ChanelId
            val channel = NotificationChannel(
                channelId,
                ChanelTitle,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        Log.d(TAG, "Build notification")
        return builder.build()
    }

    private fun updateNotification(){
        notification = initNotification()
        notificationManager.notify(NotificationId, notification)
        Log.d(TAG, "Update Notification builder")
    }

    private suspend fun update(){
        currentCity = getCurrentCityInteractor.run()
        if (currentCity != null){
            Log.d(TAG, "Get current weather")
            updateCurrentWeather()
                updateNotification()
        }
    }
}