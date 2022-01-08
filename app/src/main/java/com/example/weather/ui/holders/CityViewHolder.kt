package com.example.weather.ui.holders

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.ui.entities.CityViewEntity

private val TAG = CityViewHolder::class.java.simpleName
/**
 * Holder for city element in RecyclerView
*/
class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private lateinit var cityEntity: CityViewEntity
    private val nameTextView : TextView = itemView.findViewById(R.id.city_name_view)
    private val countryTextView : TextView = itemView.findViewById(R.id.city_country_view)
    private val iconImage : ImageView = itemView.findViewById(R.id.city_icon_view)
    private val trackIconView : ImageView = itemView.findViewById(R.id.track_icon_view)

    /** Callback called when clicking on an element */
    interface OnClickCallback{
        fun onClick(city : CityViewEntity)
    }
    /** Callback called when long clicking on an element */
    interface OnLongClickCallback{
        fun onClick(holder : RecyclerView.ViewHolder)
    }

    /** Initializes holder's elements */
    fun bind(
        cityEntity: CityViewEntity,
        clickCallback: OnClickCallback,
        longClickCallback : OnLongClickCallback){
        this.cityEntity = cityEntity
        
        itemView.setOnClickListener{
            Log.d(TAG, cityEntity.name)
            clickCallback.onClick(cityEntity)
        }
        itemView.setOnLongClickListener{
            longClickCallback.onClick(this)
            true
        }
        nameTextView.text = this.cityEntity.name
        countryTextView.text = this.cityEntity.country
        trackIconView.isVisible = this.cityEntity.isCurrent
    }
}