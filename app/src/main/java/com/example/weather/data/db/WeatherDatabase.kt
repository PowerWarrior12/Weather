package com.example.weather.data.db

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather.data.interfaces.ICitiesLocalDataSource
import com.example.weather.data.db.entities.CityEntity
import com.example.weather.data.db.entities.WeatherEntity
import com.example.weather.data.mappers.CityEntityMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

private val TAG = WeatherDatabase::class.java.simpleName

@Database(entities = [CityEntity::class, WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun cityDao() : CityDao
    abstract fun weatherDao() : WeatherDao
    private lateinit var citiesLocalDataSource: ICitiesLocalDataSource

    companion object{
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDatabase(appContext : Context, citiesLocalDataSource: ICitiesLocalDataSource) : WeatherDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                Log.d(TAG, "On get DataBase in ${Thread.currentThread().name}")
                val dbScope = CoroutineScope(Job() + Dispatchers.IO)
                val instance = Room.databaseBuilder(
                    appContext,
                    WeatherDatabase::class.java,
                    WeatherDatabase::class.java.simpleName
                )
                    .addCallback(Callback(scope = dbScope, context = appContext))
                    .build().apply {
                        this.citiesLocalDataSource = citiesLocalDataSource
                    }
                INSTANCE = instance
                return instance
            }
        }
    }

    private class Callback(
        private val scope : CoroutineScope,
        private val context: Context
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let{ instance ->
                scope.launch {
                    Log.d(TAG, "Start first load")

                    val mapper = CityEntityMapper()
                    instance.citiesLocalDataSource.loadCities().collect { list ->
                        Log.d(TAG, "List size is ${list.size}")
                        instance.cityDao().addCities(list.map { cityViewEntity ->
                            mapper.mapEntity(cityViewEntity)
                        })
                    }
                }
            }
        }
    }
}
