package com.example.weather.ui.holders

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.entities.WeatherViewEntity
import com.example.weather.ui.views.CircleDiagramView

class WeatherViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val minTemperatureTextView : TextView = itemView.findViewById(R.id.min_temperature_text)
    private val maxTemperatureTextView : TextView = itemView.findViewById(R.id.max_temperature_text)
    private val dateTextView : TextView = itemView.findViewById(R.id.date_text)
    private val pressureTextView : TextView = itemView.findViewById(R.id.pressure_text)
    private val humidityTextView : TextView = itemView.findViewById(R.id.humidity_text)
    private val humidityView : CircleDiagramView = itemView.findViewById(R.id.humidity_diagram)
    private val shareButton : ImageButton = itemView.findViewById(R.id.share_button)

    fun bind(weatherViewEntity: WeatherViewEntity, callback : OnClickCallback){
        minTemperatureTextView.text = weatherViewEntity.minTemperature.toString()
        minTemperatureTextView.jumpDrawablesToCurrentState()

        maxTemperatureTextView.text = weatherViewEntity.maxTemperature.toString()
        maxTemperatureTextView.jumpDrawablesToCurrentState()

        dateTextView.text = DateFormat.format("yyyy-MM-dd", weatherViewEntity.date)
        dateTextView.jumpDrawablesToCurrentState()

        pressureTextView.text = weatherViewEntity.pressure.toString()
        pressureTextView.jumpDrawablesToCurrentState()

        humidityTextView.text = weatherViewEntity.humidity.toString()
        humidityTextView.jumpDrawablesToCurrentState()

        humidityView.value = weatherViewEntity.humidity
        humidityView.jumpDrawablesToCurrentState()

        shareButton.setOnClickListener{
            callback.onClick("${dateTextView.text} temperature is ${maxTemperatureTextView.text}")
        }
    }


    /** Called when pressed share button */
    interface OnClickCallback{
        fun onClick(text : String)
    }
}