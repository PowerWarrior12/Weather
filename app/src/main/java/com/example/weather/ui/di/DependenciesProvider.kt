package com.example.weather.ui.di

import android.content.Context
import com.example.weather.common.Configurations
import com.example.weather.common.JSONHelper
import com.example.weather.data.api.RetrofitService
import com.example.weather.data.api.WeatherApi
import com.example.weather.data.db.WeatherDatabase
import com.example.weather.data.interfaces.ICitiesLoadDataSource
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.mappers.*
import com.example.weather.data.repositories.*
import com.example.weather.interactors.*

object DependenciesProvider {

    private var cityDB : WeatherDatabase? = null
    private var citiesJSONSource : ICitiesLoadDataSource? = null
    private var citiesRepository : CitiesRepository? = null
    private var weatherRepository : WeatherRepository? = null

    fun getCitiesInteractor(context: Context) : GetCitiesInteractor{
        return GetCitiesInteractor(getCitiesRepository(context))
    }

    fun getCityInteractor(context: Context) : GetCityInteractor{
        return GetCityInteractor(getCitiesRepository(context))
    }

    fun getCurrentCityInteractor(context: Context) : GetCurrentCityInteractor{
        return GetCurrentCityInteractor(getCitiesRepository(context))
    }

    fun getWeatherInteractor(context: Context) : GetWeatherInteractor{
        return GetWeatherInteractor(getWeatherRepository(context))
    }

    fun getSetNewCurrentCityInteractor(context: Context) : SetNewCurrentCityInteractor{
        return SetNewCurrentCityInteractor(getCitiesRepository(context))
    }

    fun getCurrentWeatherInteractor(context: Context) : GetCurrentWeatherInteractor{
        return GetCurrentWeatherInteractor(getWeatherRepository(context))
    }

    private fun getWeatherRepository(context : Context) : WeatherRepository{
        weatherRepository = weatherRepository ?: WeatherRepository(
            getWeatherRetrofitDataSource(),
            getWeatherDBDataSource(context))
        return weatherRepository as WeatherRepository
    }

    private fun getCitiesRepository(context: Context) : CitiesRepository{
        citiesRepository = citiesRepository ?: CitiesRepository(getCitiesDBDataSource(context), getCitiesDBDataSource(context))
        return citiesRepository as CitiesRepository
    }

    private fun getWeatherRetrofitDataSource() : WeatherRetrofitDataSource{
        val weatherMapper = WeatherResponseEntityMapper()
        val currentWeatherMapper = ResponseViewWeatherMapper()
        val service = getWeatherService()
        return WeatherRetrofitDataSource(service, weatherMapper, currentWeatherMapper)
    }

    private fun getWeatherService() : WeatherApi{
        val service = RetrofitService().getRetrofit(Configurations.url)
        return service.create(WeatherApi::class.java)
    }

    private fun getCitiesJSONDataSource(context: Context) : CitiesJSONDataSource {
        citiesJSONSource = citiesJSONSource ?: CitiesJSONDataSource(getJSONHelper(context, Configurations.citiesJson))
        return citiesJSONSource as CitiesJSONDataSource
    }

    private fun getCitiesDBDataSource(context: Context) : CitiesDBDataSource{
        cityDB = cityDB ?: createCityDatabase(context, getCitiesJSONDataSource(context))
        val mapperTo = CityViewEntityMapper()
        val mapperOf = CityEntityMapper()
        return CitiesDBDataSource(cityDB!!,mapperTo,mapperOf)
    }

    private fun getWeatherDBDataSource(context: Context) : WeatherDBDataSource{
        cityDB = cityDB ?: createCityDatabase(context, getCitiesJSONDataSource(context))
        val mapperTo = WeatherViewEntityMapper()
        val mapperOf = WeatherEntityMapper()
        return WeatherDBDataSource(cityDB!!, mapperTo, mapperOf)
    }

    private fun getJSONHelper(context : Context, fileName : String) : JSONHelper{
        return JSONHelper(context, fileName)
    }

    private fun createCityDatabase(
        context: Context,
        citiesJSONDataSource: CitiesJSONDataSource
    ): WeatherDatabase {
        return WeatherDatabase.getDatabase(context, citiesJSONDataSource)
    }
}