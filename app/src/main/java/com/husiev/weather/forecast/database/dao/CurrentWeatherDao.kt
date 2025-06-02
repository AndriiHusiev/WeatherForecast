package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.husiev.weather.forecast.database.entity.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao: BaseDao<CurrentWeatherEntity> {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertOrReplace(currentWeather: CurrentWeatherEntity)
	
	@Query("SELECT * FROM current_weather")
	fun loadCurrentWeather(): Flow<List<CurrentWeatherEntity>>
}