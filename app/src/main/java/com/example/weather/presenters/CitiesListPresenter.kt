package com.example.weather.presenters

import android.util.Log
import com.example.weather.interactors.GetCitiesInteractor
import com.example.weather.interactors.GetCurrentCityInteractor
import com.example.weather.presenters.interfaces.ICitiesListPresenter
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
) : MvpPresenter<ICitiesListView>(), ICitiesListPresenter {

    private var citiesList = mutableListOf<CityViewEntity>()
    private val jobLoad = Job()
    private val pScopeLoad = CoroutineScope(jobLoad + Dispatchers.IO)

    private val jobUpdate = Job()
    private val pScopeUpdate = CoroutineScope(jobUpdate + Dispatchers.IO)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        Log.d(TAG, "On first view attach")
        pScopeLoad.launch {
            viewState.startLaunch()
            getCitiesInteractor.run().collect { list ->
                updateCities(list)
            }
        }
    }

    override fun clickOnCityCard(city : CityViewEntity) {
        pScopeUpdate.launch {
            var currentCity = getCurrentCityInteractor.run()
            if (currentCity != null){
                currentCity = citiesList[citiesList.indexOf(currentCity)]
            }
            withContext(Dispatchers.Main){
                viewState.openDetailWindow(city = city, currentCity = currentCity)
            }
        }
    }

    override fun citiesListChange(cities: MutableList<CityViewEntity>) {
        citiesList = cities.toMutableList()
    }

    override fun onViewReady() {

    }

    override fun onResume() {
        Log.d(TAG, "On resume")
    }

    override fun onDestroyView() {

    }

    override fun citiesReplace(lastInd: Int, newInd: Int) {
        val item = citiesList.removeAt(lastInd)
        citiesList.add(newInd, item)
        viewState.moveCities(lastInd, newInd)
    }

    override fun onDestroy() {
        super.onDestroy()
        jobLoad.cancel()
        jobUpdate.cancel()
    }

    private suspend fun updateCities(list : List<CityViewEntity>){
        citiesList.clear()
        citiesList.addAll(list)
        if (list.isNotEmpty()){
            updateView()
            jobLoad.cancel()
        }
    }

    private suspend fun updateView(){
        withContext(Dispatchers.Main){
            viewState.updateData(citiesList.toMutableList())
            viewState.endLaunch()
        }
    }
}