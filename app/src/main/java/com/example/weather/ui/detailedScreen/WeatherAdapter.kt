package com.example.weather.ui.detailedScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.R
import com.example.weather.ui.entities.WeatherViewEntity

private val TAG = WeatherAdapter::class.java.simpleName

class WeatherAdapter : ListAdapter<WeatherViewEntity, WeatherViewHolder>(WeatherItemCallback) {

    private lateinit var onShareButtonClickCallback : WeatherViewHolder.OnClickCallback

    object WeatherItemCallback : DiffUtil.ItemCallback<WeatherViewEntity>(){
        override fun areItemsTheSame(oldItem: WeatherViewEntity, newItem: WeatherViewEntity): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: WeatherViewEntity, newItem: WeatherViewEntity): Boolean {
            return oldItem.date == newItem.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.weather_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position),onShareButtonClickCallback)
    }

    companion object{
        fun getInstance(
            onShareButtonClickCallback : WeatherViewHolder.OnClickCallback
        ) : WeatherAdapter{
            return WeatherAdapter().apply {
                this.onShareButtonClickCallback = onShareButtonClickCallback
            }
        }
    }
}