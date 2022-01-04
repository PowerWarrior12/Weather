package com.example.weather.ui.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.reflect.typeOf

data class CityViewEntity(
    @PrimaryKey
    val id : Int,
    val coord : Coord,
    var name : String = "",
    var country : String = "",
    var isCurrent : Boolean = false) {

    data class Coord(
        val lon : Double,
        val lat : Double
    ){}

    override fun equals(other: Any?): Boolean =
        (other is CityViewEntity) && this.id == other.id


    override fun hashCode(): Int {
        var result = id
        result = 31 * result + coord.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + country.hashCode()
        result = 31 * result + isCurrent.hashCode()
        return result
    }
}