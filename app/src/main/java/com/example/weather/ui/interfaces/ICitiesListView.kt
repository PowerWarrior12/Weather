package com.example.weather.ui.interfaces

import com.example.weather.ui.entities.CityViewEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType
/**
 * View interface for cities list view
*/
@StateStrategyType(value = SkipStrategy::class)
interface ICitiesListView : MvpView {
    fun updateData(cities : MutableList<CityViewEntity>)
    fun openDetailWindow(cityId : Int)
    fun showMessage(message : Int)
    fun showMessage(message : String)
    fun moveCities(firstCityInd : Int, secondCityInd : Int, list : List<CityViewEntity>)
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun showLoader()
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun hideLoader()
}