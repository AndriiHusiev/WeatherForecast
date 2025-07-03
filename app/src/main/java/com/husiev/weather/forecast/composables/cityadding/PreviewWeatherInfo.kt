package com.husiev.weather.forecast.composables.cityadding

import android.content.Context
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.main.NO_DATA
import kotlin.math.roundToInt

data class PreviewWeatherInfo(
	val id: Int,
	val name: String,
	val temperature: Float,
	val weatherId: Int,
	val weatherIcon: String,
)

data class PreviewCityInfo(
	val id: Int,
	val name: String,
	val temperature: String,
	val weatherDesc: String,
	val weatherIcon: String,
)

fun List<PreviewWeatherInfo>.asExternalModel(context: Context): List<PreviewCityInfo> {
	val list = mutableListOf<PreviewCityInfo>()
	val wId = context.resources.getIntArray(R.array.weather_id).toList()
	val desc = context.resources.getStringArray(R.array.weather_desc)
	
	this.forEach { 
		list.add(PreviewCityInfo(
			id = it.id,
			name = it.name,
			temperature = it.temperature.roundToInt().toString() + "°",
			weatherDesc = desc.getOrElse(wId.indexOf(it.weatherId)) { NO_DATA },
			weatherIcon = it.weatherIcon
		))
	}
	
	return list
}