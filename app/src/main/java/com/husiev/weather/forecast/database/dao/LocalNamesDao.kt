package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.husiev.weather.forecast.database.entity.LocalNamesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalNamesDao: BaseDao<LocalNamesEntity> {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertAll(list: List<LocalNamesEntity>)
	
	@Query("SELECT * FROM local_names WHERE city_id = :cityId AND locale = :locale")
	fun loadLocalName(cityId: Int, locale: String): Flow<List<LocalNamesEntity>>
}