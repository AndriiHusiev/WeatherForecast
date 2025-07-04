package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(vararg obj: T)
	
	@Delete
	suspend fun delete(vararg obj: T)
	
	@Update
	suspend fun update(vararg obj: T)
}