package com.husiev.weather.forecast.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCityInfo (
	val name: String,
	val lat: Float,
	val lon: Float,
	val country: String,
	val state: String? = null,
	@SerialName(value = "local_names")
	val localNames: Map<String, String?>? = null,
)