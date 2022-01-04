package com.example.weather.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.fragments.CitiesListFragment
import com.example.weather.ui.fragments.CitiesListFragment.IOpenFragment
import com.example.weather.ui.fragments.WeatherFragment
import com.example.weather.R
import moxy.MvpAppCompatActivity

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), IOpenFragment{

    private val citiesListFragment = CitiesListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.cities_container)
        if (currentFragment == null){
            openFragment(citiesListFragment)
        }
    }

    private fun openFragment(fragment : Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.cities_container, fragment)
            .commit()
    }

    override fun run(city : CityViewEntity, currentCity : CityViewEntity?) = openFragment(WeatherFragment.newInstance(city, currentCity))
}