package com.husiev.weather.forecast.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.husiev.weather.forecast.composables.cityadding.PreviewWeatherInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface PreviewWeatherDao {
	@Query(
		"SELECT cities.id, cities.name, current_weather.temperature, " +
				"current_weather.weather_id AS weatherId, " +
				"current_weather.weather_icon AS weatherIcon " +
				"FROM cities, current_weather " +
				"WHERE cities.id = current_weather.city_id"
	)
	fun loadPreviewWeather(): Flow<List<PreviewWeatherInfo>>
}