package com.example.weather.data.db.entities

import androidx.room.*
import java.util.*

@Entity(
    tableName = "weather",
    foreignKeys = [ForeignKey(
        entity = CityEntity::class,
        parentColumns = ["id"],
        childColumns = ["cityId"]
    )],
    indices = [
        Index(value = ["cityId"])
    ]
)
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    val date : Date,
    val minTemperature : Double,
    val maxTemperature : Double,
    val pressure : Int,
    val humidity : Int,
    @ColumnInfo(name = "cityId")
    val cityId : Int?
)
