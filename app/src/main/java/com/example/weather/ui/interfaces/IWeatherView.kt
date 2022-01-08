package com.example.weather.ui.interfaces

import com.example.weather.ui.entities.WeatherViewEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * View interface for weather
*/
@StateStrategyType(value = SkipStrategy::class)
interface IWeatherView : MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun updateData(weatherView : List<WeatherViewEntity>)
    fun showMessage(message : Int)
    fun showMessage(message : String)
    fun startCurrentWeatherService()
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun setCurrentCityButtonEnable(enable : Boolean)
    fun shareInformation(text : String)
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun setCityName(cityName : String)
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun startLaunch()
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun endLaunch()
}