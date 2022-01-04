package com.example.weather.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.weather.R
import com.example.weather.ui.entities.WeatherViewEntity
import com.example.weather.presenters.WeatherPresenter
import com.example.weather.ui.WeatherApplication
import com.example.weather.ui.adapters.WeatherAdapter
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.interfaces.IWeatherView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.weather.ui.holders.WeatherViewHolder
import com.example.weather.ui.services.CurrentWeatherWorker
import java.util.concurrent.TimeUnit


class WeatherFragment : MvpAppCompatFragment(), IWeatherView {

    @InjectPresenter
    lateinit var weatherPresenter: WeatherPresenter

    private lateinit var city : CityViewEntity
    private var currentCity : CityViewEntity? = null

    private lateinit var weatherViewPager2: ViewPager2
    private lateinit var currentCityButton : Button
    private lateinit var weightTableLayout : TabLayout
    private lateinit var weatherAdapter : WeatherAdapter

    @ProvidePresenter
    fun presenterProvider() : WeatherPresenter{
        return WeatherPresenter(
            ((requireActivity().application) as WeatherApplication).getWeatherInteractor,
            ((requireActivity().application) as WeatherApplication).getSetNewCurrentCityInteractor,
            city,
            currentCity
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weather_fragment, container, false)
        initView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    override fun updateData(weatherView : List<WeatherViewEntity>) {
        weatherAdapter.submitList(weatherView)
        if (weatherView.isNotEmpty()){
            TabLayoutMediator(weightTableLayout, weatherViewPager2){ tab, position ->
                tab.text = DateFormat.format("E\nyyyy-MM-dd",weatherView[position].date)
            }.attach()
        }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun startCurrentWeatherService() {
        ((requireActivity().application) as WeatherApplication).startCurrentWeatherService()
    }

    override fun setCurrentCityButtonEnable(enable: Boolean) {
        currentCityButton.isEnabled = enable
    }

    override fun shareInformation(text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "*/*"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(intent, "Share with Friends"));
    }

    private fun initView(view : View){
        weatherViewPager2 = view.findViewById(R.id.weather_view_pager)
        weightTableLayout = view.findViewById(R.id.weather_table)
        currentCityButton = view.findViewById(R.id.current_city_button)
        currentCityButton.apply {
            setOnClickListener{
                weatherPresenter.onCurrentCityButtonPressed()
            }
            text = city.name
        }
    }

    private fun setupViewPager(){
        weatherAdapter = WeatherAdapter.getInstance(object : WeatherViewHolder.OnClickCallback{
            override fun onClick(text: String) {
                weatherPresenter.onShareButtonPressed(text)
            }
        })
        weatherViewPager2.apply {
            adapter = weatherAdapter
        }
    }

    companion object{
        fun newInstance(city : CityViewEntity, currentCity : CityViewEntity?) : WeatherFragment{
            return WeatherFragment().apply {
                this.city = city
                this.currentCity = currentCity
            }
        }
    }
}