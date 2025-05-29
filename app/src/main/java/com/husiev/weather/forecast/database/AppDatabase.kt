package com.husiev.weather.forecast.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.husiev.weather.forecast.database.dao.CitiesDao
import com.husiev.weather.forecast.database.dao.LocalNamesDao
import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.database.entity.LocalNamesEntity

@Database(
	entities = [
		CityEntity::class,
		LocalNamesEntity::class,
	],
	version = 1,
	exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
	
	abstract fun citiesDao(): CitiesDao
	abstract fun localNamesDao(): LocalNamesDao
	
}