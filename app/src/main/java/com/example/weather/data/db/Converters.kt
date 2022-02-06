package com.example.weather.data.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromBoolean(boolean : Boolean) : String{
        return boolean.toString()
    }
    @TypeConverter
    fun toBoolean(string : String) : Boolean{
        return string.toBoolean()
    }

    @TypeConverter
    fun toDate(date : Long) : Date {
        return Date(date)
    }

    @TypeConverter
    fun fromDate(date : Date) : Long{
        return date.time
    }
}