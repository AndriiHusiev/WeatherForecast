package com.husiev.weather.forecast.network

import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.database.entity.LocalNamesEntity
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

fun NetworkCityInfo.asEntity(id: Int = 0) = CityEntity(
	id = id,
	name = this.name,
	lat = this.lat,
	lon = this.lon,
	country = this.country,
	state = this.state,
)

fun NetworkCityInfo.asLocalNamesEntity(cityId: Int): List<LocalNamesEntity> {
	val list = mutableListOf<LocalNamesEntity>()
	
	this.localNames?.forEach { item ->
		item.value?.let {
			list.add(LocalNamesEntity(
				cityId = cityId,
				locale = item.key,
				name = it,
			))
		}
	}
	
	return list
}