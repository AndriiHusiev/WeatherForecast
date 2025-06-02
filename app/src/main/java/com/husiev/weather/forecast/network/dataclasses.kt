package com.husiev.weather.forecast.network

import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.database.entity.CurrentWeatherEntity
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

@Serializable
data class NetworkTodayInfo (
	val id: Int,
	val name: String,
	val base: String,
	val visibility: Int,
	val dt: Int,
	val cod: Int,
	val timezone: Int,
	val coord: Coordinates,
	val weather: List<Weather>,
	val main: Main,
	val wind: Wind? = null,
	val rain: Rain? = null,
	val clouds: Clouds? = null,
	val snow: Snow? = null,
	val sys: Sys
)

fun NetworkTodayInfo.asEntity(cityId: Int) = CurrentWeatherEntity(
	cityId = cityId,
	visibility = this.visibility,
	weatherId = this.weather[0].id,
	weatherGroup = this.weather[0].main,
	weatherDescription = this.weather[0].description,
	weatherIcon = this.weather[0].icon,
	temperature = this.main.temp,
	feelsLike = this.main.feelsLike,
	pressure = this.main.groundLevel,
	humidity = this.main.humidity,
	sunrise = this.sys.sunrise + this.timezone,
	sunset = this.sys.sunset + this.timezone,
	windSpeed = this.wind?.speed,
	windDeg = this.wind?.deg,
	windGust = this.wind?.gust,
	cloudiness = this.clouds?.all,
	rain = this.rain?.oneHour,
	snow = this.snow?.oneHour
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

@Serializable
data class Coordinates (
	val lon: Float,
	val lat: Float,
)

@Serializable
data class Weather(
	val id: Int,
	val main: String,
	val description: String,
	val icon: String,
)

@Serializable
data class Main(
	val pressure: Int,
	val humidity: Int,
	val temp: Float,
	@SerialName(value = "feels_like")
	val feelsLike: Float,
	@SerialName(value = "temp_min")
	val tempMin: Float,
	@SerialName(value = "temp_max")
	val tempMax: Float,
	@SerialName(value = "sea_level")
	val seaLevel: Int,
	@SerialName(value = "grnd_level")
	val groundLevel: Int,
	@SerialName(value = "temp_kf")
	val tempKf: Float? = null,
)

@Serializable
data class Wind(
	val speed: Float,
	val deg: Int,
	val gust: Float? = null,
)

@Serializable
data class Rain(
	@SerialName(value = "1h")
	val oneHour: Float,
)

@Serializable
data class Snow(
	@SerialName(value = "1h")
	val oneHour: Float,
)

@Serializable
data class Clouds(
	val all: Int,
)

@Serializable
data class Sys(
	val type: Int? = null,
	val id: Int? = null,
	val message: String? = null,
	val sunrise: Int,
	val sunset: Int,
	val country: String,
)