package com.husiev.weather.forecast.composables.main

import com.husiev.weather.forecast.composables.cityadding.PreviewCityInfo

const val NO_DATA = "--"

data class WeatherInfo(
	val cityInfo: CityInfo? = null,
	val current: CurrentWeatherInfo = CurrentWeatherInfo(),
	val briefForecast: List<ForecastBriefInfo> = emptyList(),
	val fullForecast: List<ForecastWeatherInfo> = emptyList(),
	val preview: List<PreviewCityInfo> = emptyList(),
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
	val temperature: String = NO_DATA,
	val feelsLike: String = NO_DATA,
	
	val weatherId: Int = -1,
	val weatherIcon: String = NO_DATA,
	
	val pressure: String = NO_DATA,
	val humidity: String = NO_DATA,
	val visibility: String = NO_DATA,
	
	val sunrise: Float = 0f,
	val sunset: Float = 0f,
	val sunriseTime: String = NO_DATA,
	val sunsetTime: String = NO_DATA,
	
	val windSpeed: String = NO_DATA,
	val windDeg: Float? = null,
	val windDir: String = NO_DATA,
	val windGust: String = NO_DATA,
	
	val cloudiness: String = NO_DATA,
	val rain: String = NO_DATA,
	val snow: String = NO_DATA
)

data class ForecastBriefInfo(
	val index: Int = -1,
	val date: String,
	val dayOfWeek: String,
	val weatherIcon: String,
	val temperatureRange: String,
)

data class ForecastWeatherInfo(
	val index: Int = -1,
	val date: String = NO_DATA,
	val dateWeekDay: String = NO_DATA,
	val time: String = NO_DATA,
	val temperature: String = NO_DATA,
	val feelsLike: String = NO_DATA,
	
	val weatherId: Int = -1,
	val weatherIcon: String = NO_DATA,
	
	val pressure: String = NO_DATA,
	val humidity: String = NO_DATA,
	val visibility: String = NO_DATA,
	
	val windSpeed: String = NO_DATA,
	val windDeg: Float? = null,
	val windDir: String = NO_DATA,
	val windGust: String = NO_DATA,
	
	val cloudiness: String = NO_DATA,
	val rain: String = NO_DATA,
	val snow: String = NO_DATA,
	val pop: String = NO_DATA,
)