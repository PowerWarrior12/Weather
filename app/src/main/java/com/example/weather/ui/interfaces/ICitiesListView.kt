package com.example.weather.ui.interfaces

import com.example.weather.ui.entities.CityViewEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
/**
 * View interface for cities list view
*/
@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ICitiesListView : MvpView {
    fun updateData(cities : MutableList<CityViewEntity>)
    @StateStrategyType(value = SkipStrategy::class)
    fun openDetailWindow(city : CityViewEntity, currentCity : CityViewEntity?)
    @StateStrategyType(value = SkipStrategy::class)
    fun showMessage(message : Int)
    @StateStrategyType(value = SkipStrategy::class)
    fun showMessage(message : String)
    @StateStrategyType(value = SkipStrategy::class)
    fun moveCities(firstCityInd : Int, secondCityInd : Int)
    fun startLaunch()
    fun endLaunch()
}