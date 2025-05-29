package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.husiev.weather.forecast.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao: BaseDao<CityEntity> {
	@Query("SELECT * FROM cities")
	fun loadCitiesList(): Flow<List<CityEntity>>
	
	@Query("SELECT * FROM cities WHERE lat = :lat AND lon = :lon")
	fun loadCity(lat: Float, lon: Float): Flow<CityEntity>
}