package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.ui.entities.CityViewEntity
import com.example.weather.ui.holders.CityViewHolder


private val TAG = CitiesAdapter::class.java.simpleName

/**
 * Adapter for CitiesRecyclerView,
 * must be initialized via newInstance.
 * Implements ItemTouchHelperAdapter to handle the movement of list items
 */
class CitiesAdapter() : RecyclerView.Adapter<CityViewHolder>() {

    private lateinit var onItemClickCallback : CityViewHolder.OnClickCallback
    private lateinit var onItemLongClickCallback : CityViewHolder.OnLongClickCallback

    private val cities : MutableList<CityViewEntity> = mutableListOf()

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.city_element, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(

            cityEntity = cities[position],
            clickCallback = onItemClickCallback,
            longClickCallback = onItemLongClickCallback)
    }

    fun submitList(list : MutableList<CityViewEntity>){
        val callback = CityItemCallback(cities, list)
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)

        cities.clear()
        cities.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setList(list : List<CityViewEntity>){
        cities.clear()
        cities.addAll(list)
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

    class CityItemCallback(
        private val oldCities: List<CityViewEntity>,
        private val newCities: List<CityViewEntity>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldCities.size
        }

        override fun getNewListSize(): Int {
            return newCities.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCities[oldItemPosition].id == newCities[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldCities[oldItemPosition].id == newCities[newItemPosition].id
        }
    }
}