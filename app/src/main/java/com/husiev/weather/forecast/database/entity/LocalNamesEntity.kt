package com.husiev.weather.forecast.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
	tableName = "local_names",
	foreignKeys = [ForeignKey(
		entity = CityEntity::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("city_id"),
		onDelete = ForeignKey.CASCADE,
	)],
	indices = [Index(value = ["city_id", "locale"], unique = true)]
)
data class LocalNamesEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	@ColumnInfo(name = "city_id")
	val cityId: Int,
	val locale: String,
	val name: String,
)
