package com.example.weather.presenters

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather.interactors.GetWeatherInteractor
import com.example.weather.interactors.SetNewCurrentCityInteractor
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import com.example.weather.ui.interfaces.IWeatherView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import kotlinx.coroutines.flow.*

@InjectViewState
class WeatherPresenter(
    private val weatherInteractor: GetWeatherInteractor,
    private val setNewCurrentCityInteractor: SetNewCurrentCityInteractor,
    private val city: CityViewEntity,
    private val currentCity : CityViewEntity?
) : MvpPresenter<IWeatherView>() {

    private val job = Job()
    private val pScope = CoroutineScope(job + Dispatchers.IO)
    private var weather = listOf<WeatherViewEntity>()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (city.isCurrent)
            viewState.setCurrentCityButtonEnable(false)
        pScope.launch {
            weatherInteractor.run(city, pScope).collect{
                weather = it
                withContext(Dispatchers.Main){
                    viewState.updateData(weather.toList())
                }
            }
        }
    }

    fun onCurrentCityButtonPressed(){
        pScope.launch {
            setNewCurrentCityInteractor.run(city, currentCity)
            withContext(Dispatchers.Main){
                viewState.startCurrentWeatherService()
                viewState.setCurrentCityButtonEnable(false)
                viewState.showMessage("New city selected")
            }
        }
    }

    fun onShareButtonPressed(text : String){
        viewState.shareInformation(text)
    }
}