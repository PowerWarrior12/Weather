package com.example.weather.ui.detailedScreen

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.ui.entities.WeatherViewEntity
import com.example.weather.ui.views.CircleDiagramView

private val TAG = WeatherViewHolder::class.java.simpleName

class WeatherViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val minTemperatureTextView : TextView = itemView.findViewById(R.id.min_temperature_text)
    private val maxTemperatureTextView : TextView = itemView.findViewById(R.id.max_temperature_text)
    private val dateTextView : TextView = itemView.findViewById(R.id.date_text)
    private val pressureTextView : TextView = itemView.findViewById(R.id.pressure_text)
    private val humidityTextView : TextView = itemView.findViewById(R.id.humidity_text)
    private val humidityView : CircleDiagramView = itemView.findViewById(R.id.humidity_diagram)
    private val shareButton : ImageButton = itemView.findViewById(R.id.share_button)
    private lateinit var weatherViewEntity: WeatherViewEntity

    @SuppressLint("SetTextI18n")
    fun bind(weatherViewEntity: WeatherViewEntity, callback : OnClickCallback){
        this.weatherViewEntity = weatherViewEntity

        minTemperatureTextView.text = "${this.weatherViewEntity.minTemperature} K°"
        minTemperatureTextView.jumpDrawablesToCurrentState()

        maxTemperatureTextView.text = "${this.weatherViewEntity.maxTemperature} K°"
        maxTemperatureTextView.jumpDrawablesToCurrentState()

        dateTextView.text = DateFormat.format("yyyy-MM-dd", this.weatherViewEntity.date)
        dateTextView.jumpDrawablesToCurrentState()

        pressureTextView.text = this.weatherViewEntity.pressure.toString()
        pressureTextView.jumpDrawablesToCurrentState()

        humidityTextView.text = "${this.weatherViewEntity.humidity} %"
        humidityTextView.jumpDrawablesToCurrentState()

        humidityView.value = this.weatherViewEntity.humidity
        humidityView.jumpDrawablesToCurrentState()

        shareButton.setOnClickListener{
            callback.onClick(this.weatherViewEntity)
        }
    }


    /** Called when pressed share button */
    interface OnClickCallback{
        fun onClick(weatherViewEntity: WeatherViewEntity)
    }
}