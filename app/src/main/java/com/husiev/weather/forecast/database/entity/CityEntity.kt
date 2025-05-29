package com.husiev.weather.forecast.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
)

data class CityInfo (
	val id: Int,
	val lat: Float,
	val lon: Float,
	val country: String,
	val state: String?,
	val name: String,
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