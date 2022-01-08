package com.example.weather.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "city",
    indices = [
        Index(value = ["isCurrent"])
    ]
)
data class CityEntity(
    @PrimaryKey
    val id : Int,
    val lon : Double,
    val lat : Double,
    var name : String = "",
    var country : String = "",
    var isCurrent : Boolean = false) {
}