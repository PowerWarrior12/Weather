package com.example.weather.ui.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weather.R
import com.example.weather.ui.WeatherApplication
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.CurrentWeatherViewEntity
import kotlinx.coroutines.*

private val TAG = CurrentWeatherWorker::class.simpleName

class CurrentWeatherWorker(private val appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private val getCurrentWeatherInteractor = (appContext as WeatherApplication).getCurrentWeatherInteractor
    private val getCurrentCityInteractor = (appContext as WeatherApplication).getCurrentCityInteractor
    private val notificationManager = appContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    private var currentCity : CityViewEntity? = null
    private var currentWeather : CurrentWeatherViewEntity? = null
    private lateinit var notification: Notification
    private val job = Job()
    private val pScope = CoroutineScope(job + Dispatchers.IO)

    override fun doWork(): Result {
        update()
        return Result.success()
    }

    private suspend fun updateCurrentWeather(){
        currentWeather = currentCity?.let {
            getCurrentWeatherInteractor.run(city = it)
        }
        Log.d(TAG, "${currentWeather?.temperature}")
    }


    private fun initNotificationBuilder(): NotificationCompat.Builder {
        return NotificationCompat.Builder(appContext, "Your_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(currentCity?.name ?: "NONE")
            .setAutoCancel(false)
            .setContentText("Current temperature is ${currentWeather?.temperature ?: "NONE"}")
    }
    private fun initNotification() : Notification {
        val builder = initNotificationBuilder()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "Your_channel_id"
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        return builder.build()
    }

    private fun updateNotification(){
        notification = initNotification()
        notificationManager.notify(1, notification)
    }

    private fun update(){
        pScope.launch {
            currentCity = getCurrentCityInteractor.run()
            updateCurrentWeather()
            withContext(Dispatchers.Main){
                updateNotification()
            }
        }
    }
}