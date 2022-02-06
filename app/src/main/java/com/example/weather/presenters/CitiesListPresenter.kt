package com.example.weather.presenters

import android.util.Log
import com.example.weather.interactors.GetCitiesInteractor
import com.example.weather.interactors.GetCurrentCityInteractor
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.interfaces.ICitiesListView
import moxy.InjectViewState
import moxy.MvpPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private val TAG = CitiesListPresenter::class.java.simpleName

/**
 *Presenter for cities list
 */
@InjectViewState
class CitiesListPresenter(
    private val getCitiesInteractor: GetCitiesInteractor,
    private val getCurrentCityInteractor: GetCurrentCityInteractor
) : MvpPresenter<ICitiesListView>() {

    private var citiesList = mutableListOf<CityViewEntity>()
    private val jobLoad = Job()
    private val pScopeLoad = CoroutineScope(jobLoad + Dispatchers.IO)

    private val jobUpdate = Job()
    private val pScopeUpdate = CoroutineScope(jobUpdate + Dispatchers.IO)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        Log.d(TAG, "On first view attach")
        pScopeLoad.launch {
            viewState.showLoader()
            getCitiesInteractor.run().collect { list ->
                updateCities(list)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        jobLoad.cancel()
        jobUpdate.cancel()
    }

    fun clickOnCityCard(city : CityViewEntity) {
        viewState.openDetailWindow(city.id)
    }

    fun onResume() {
        pScopeUpdate.launch {
            val currentCity = getCurrentCityInteractor.run()
            withContext(Dispatchers.Main){
                updateCurrentCities(currentCity)
            }
        }
        viewState.updateData(citiesList.toMutableList())
        Log.d(TAG, "On resume")
    }

    fun citiesReplace(lastInd: Int, newInd: Int) {
        val item = citiesList.removeAt(lastInd)
        citiesList.add(newInd, item)
        viewState.moveCities(lastInd, newInd, citiesList)
    }

    private suspend fun updateCities(list : List<CityViewEntity>){
        citiesList.clear()
        citiesList.addAll(list)
        if (list.isNotEmpty()){
            withContext(Dispatchers.Main){
                viewState.updateData(citiesList.toMutableList())
                viewState.hideLoader()
            }
            jobLoad.cancel()
        }
    }
    private fun updateCurrentCities(currentCity : CityViewEntity?){
        for (city in citiesList){
            if (currentCity?.id == city.id){
                city.isCurrent = true
            }
            else if (city.isCurrent){
                city.isCurrent = false
            }
        }
        viewState.updateData(citiesList)
    }
}