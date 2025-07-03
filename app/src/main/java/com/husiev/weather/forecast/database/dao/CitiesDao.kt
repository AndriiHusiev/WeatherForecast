package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.husiev.weather.forecast.database.entity.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitiesDao: BaseDao<CityEntity> {
	@Update
	suspend fun updateCities(list: List<CityEntity>)
	
	@Query("SELECT id FROM cities WHERE selected = 1 LIMIT 1")
	suspend fun getSelectedCityId(): Int?
	
	@Query("SELECT * FROM cities")
	fun loadCitiesList(): Flow<List<CityEntity>>
}