package com.husiev.weather.forecast.composables.main

const val NO_DATA = "--"

data class WeatherInfo(
	val cityInfo: CityInfo? = null,
	val current: CurrentWeatherInfo = CurrentWeatherInfo(),
)

data class CityInfo (
	val id: Int,
	val lat: Float,
	val lon: Float,
	val country: String,
	val state: String?,
	val name: String,
)

data class CurrentWeatherInfo(
	val cityId: Int = 0,
	val temperature: String = NO_DATA,
	val feelsLike: String = NO_DATA,
	
	val weatherId: Int = -1,
	val weatherIcon: String = NO_DATA,
	val weatherGroup: String = NO_DATA,
	val weatherDescription: String = NO_DATA,
	
	val pressure: String = NO_DATA,
	val humidity: String = NO_DATA,
	val visibility: String = NO_DATA,
	
	val sunrise: Int = 0,
	val sunset: Int = 0,
	
	val windSpeed: String = NO_DATA,
	val windDeg: Float? = null,
	val windDir: String = NO_DATA,
	val windGust: String = NO_DATA,
	
	val cloudiness: String = NO_DATA,
	val rain: String = NO_DATA,
	val snow: String = NO_DATA
)
