package com.example.weather.ui.helpers
/**
 * Interface for handling interaction with elements
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition : Int, toPosition : Int) : Boolean
}