package com.example.weather.ui.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.weather.ui.fragments.CitiesListFragment
import com.example.weather.ui.fragments.CitiesListFragment.IOpenFragment
import com.example.weather.ui.fragments.WeatherFragment
import com.example.weather.R
import com.example.weather.ui.adapters.WeatherAdapter
import moxy.MvpAppCompatActivity

private val TAG = MainActivity::class.java.simpleName

class MainActivity : MvpAppCompatActivity(R.layout.activity_main), IOpenFragment{

    private val citiesListFragment = CitiesListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.cities_frame_layout)
        if (currentFragment == null){
            openFragment(citiesListFragment, false)
        }
    }

    private fun openFragment(fragment : Fragment, addToBackStack : Boolean){
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.anim_top_in, R.anim.anim_bottom_in,
                R.anim.anim_top_in, R.anim.anim_bottom_in)
            replace(R.id.cities_frame_layout, fragment)
            // Adding a transaction to the backStack
            if (addToBackStack){
                addToBackStack(null)
            }
        }
            .commit()
    }

    override fun run(cityId : Int) {
        openFragment(WeatherFragment.newInstance(cityId), true)
    }
}