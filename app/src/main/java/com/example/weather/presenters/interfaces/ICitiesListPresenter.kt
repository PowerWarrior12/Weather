package com.example.weather.presenters.interfaces

import com.example.weather.ui.entities.CityViewEntity

interface ICitiesListPresenter {
    fun clickOnCityCard(city : CityViewEntity)
    fun citiesListChange(cities : MutableList<CityViewEntity>)
    fun onViewReady()
    fun onResume()
    fun onDestroyView()
    fun citiesReplace(lastInd : Int, newInd : Int)
}