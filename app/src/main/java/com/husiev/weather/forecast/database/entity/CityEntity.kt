package com.husiev.weather.forecast.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.husiev.weather.forecast.composables.main.CityInfo

@Entity(
	tableName = "cities",
	indices = [Index(value = ["lat", "lon"], unique = true)]
)
data class CityEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	val name: String,
	val lat: Float,
	val lon: Float,
	val country: String,
	val state: String? = null,
	val selected: Boolean,
)

fun CityEntity.asExternalModel(name: String): CityInfo {
	return CityInfo(
		id = this.id,
		lat = this.lat,
		lon = this.lon,
		country = this.country,
		state = this.state,
		name = name,
	)
}