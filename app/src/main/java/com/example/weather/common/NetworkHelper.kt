package com.example.weather.common

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather.ui.WeatherApplication

object NetworkHelper {
    @RequiresApi(Build.VERSION_CODES.M)
    fun isNetworkAvailable() : Boolean{
        val cm = WeatherApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null
    }
}