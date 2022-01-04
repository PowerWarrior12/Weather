package com.example.weather.ui.interfaces

import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * View interface for weather
*/
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface IWeatherView : MvpView {
    fun updateData(weatherView : List<WeatherViewEntity>)
    @StateStrategyType(value = SkipStrategy::class)
    fun showMessage(message : Int)
    @StateStrategyType(value = SkipStrategy::class)
    fun showMessage(message : String)
    @StateStrategyType(value = SkipStrategy::class)
    fun startCurrentWeatherService()
    fun setCurrentCityButtonEnable(enable : Boolean)
    @StateStrategyType(value = SkipStrategy::class)
    fun shareInformation(text : String)
}