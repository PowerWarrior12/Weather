package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.weather.R
import com.example.weather.data.db.entities.CityEntity
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.holders.CityViewHolder


private val TAG = CitiesAdapter::class.java.simpleName

/**
 * Adapter for CitiesRecyclerView,
 * must be initialized via newInstance.
 * Implements ItemTouchHelperAdapter to handle the movement of list items
 */
class CitiesAdapter() : ListAdapter<CityViewEntity, CityViewHolder>(CityItemCallback) {

    private lateinit var onItemClickCallback : CityViewHolder.OnClickCallback
    private lateinit var onItemLongClickCallback : CityViewHolder.OnLongClickCallback

    object CityItemCallback : DiffUtil.ItemCallback<CityViewEntity>(){
        override fun areItemsTheSame(oldItem: CityViewEntity, newItem: CityViewEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CityViewEntity, newItem: CityViewEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(
            cityEntity = getItem(position),
            clickCallback = onItemClickCallback,
            longClickCallback = onItemLongClickCallback)
    }

    companion object{
        fun newInstance(onItemClickCallback : CityViewHolder.OnClickCallback,
                        onLongItemClickCallback : CityViewHolder.OnLongClickCallback) : CitiesAdapter{
            return CitiesAdapter().apply {

                this.onItemClickCallback = onItemClickCallback
                this.onItemLongClickCallback = onLongItemClickCallback
            }
        }
    }
}