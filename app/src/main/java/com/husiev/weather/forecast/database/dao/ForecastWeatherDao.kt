package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.husiev.weather.forecast.database.entity.ForecastWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastWeatherDao: BaseDao<ForecastWeatherEntity> {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertOrReplace(list: List<ForecastWeatherEntity>)
	
	@Query("SELECT * FROM forecast_weather")
	fun loadForecastWeather(): Flow<List<ForecastWeatherEntity>>
}