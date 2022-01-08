package com.example.weather.presenters

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather.interactors.GetCityInteractor
import com.example.weather.interactors.GetCurrentWeatherInteractor
import com.example.weather.interactors.GetWeatherInteractor
import com.example.weather.interactors.SetNewCurrentCityInteractor
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import com.example.weather.ui.interfaces.IWeatherView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import kotlinx.coroutines.flow.*

private val TAG = WeatherPresenter::class.java.simpleName

@InjectViewState
class WeatherPresenter(
    private val weatherInteractor: GetWeatherInteractor,
    private val setNewCurrentCityInteractor: SetNewCurrentCityInteractor,
    private val getCityInteractor : GetCityInteractor,
    private val cityId : Int
) : MvpPresenter<IWeatherView>() {

    private val job = Job()
    private val pScope = CoroutineScope(job + Dispatchers.IO)
    private var weather = listOf<WeatherViewEntity>()
    private lateinit var city : CityViewEntity

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        pScope.launch {
            withContext(Dispatchers.Main){
                viewState.startLaunch()
            }
            city = getCityInteractor.run(cityId)!!
            withContext(Dispatchers.Main){
                viewState.setCityName(cityName = city.name)
                if (city.isCurrent){
                    viewState.setCurrentCityButtonEnable(false)
                }
            }
            weatherInteractor.run(city, pScope).collect{
                weather = it
                withContext(Dispatchers.Main){
                    viewState.updateData(weather.toList())
                    if (weather.isNotEmpty())
                        viewState.endLaunch()
                }
            }
        }
    }

    fun onCurrentCityButtonPressed(){
        pScope.launch {
            setNewCurrentCityInteractor.run(city)
            withContext(Dispatchers.Main){
                viewState.startCurrentWeatherService()
                viewState.setCurrentCityButtonEnable(false)
                viewState.showMessage("Отмечен новый город")
            }
        }
    }

    fun onShareButtonPressed(weather : WeatherViewEntity){
        viewState.shareInformation("Погода на ${weather.date} : температура ${weather.minTemperature}")
    }

}