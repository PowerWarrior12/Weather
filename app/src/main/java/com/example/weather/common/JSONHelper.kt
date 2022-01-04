package com.example.weather.common

import android.content.Context
import com.example.weather.data.db.entities.CityEntity
import com.example.weather.ui.entities.CityViewEntity
import com.google.gson.Gson;
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception

class JSONHelper (private val context: Context, private val fileName: String) {
    fun importAll() : List<CityViewEntity>{
        try {
            val streamReader = InputStreamReader(context.assets.open(fileName))
            val gson = Gson()
            return gson.fromJson(streamReader, Array<CityViewEntity>::class.java).toList()
        }
        catch (exception : FileNotFoundException){
            exception.printStackTrace()
        }
        catch (exception : IOException){
            exception.printStackTrace()
        }
        catch (exception : Exception){
            exception.printStackTrace()
        }
        return emptyList()
    }
}