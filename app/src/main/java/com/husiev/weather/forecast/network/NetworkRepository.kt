package com.husiev.weather.forecast.network

import android.util.Log
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
	private val networkService: NetworkApiService,
) {
	
	fun getCitiesList(search: String) = flow {
		emit(SearchResultUiState.Loading)
		try {
			val response = networkService.getCities(search)
			emit(SearchResultUiState.Success(response))
		} catch (exception: IOException) {
			logDebugOut("NetworkRepository", "Failed to get list of cities", exception)
			emit(SearchResultUiState.LoadFailed)
		}
	}
	
	suspend fun getCurrentWeather(latitude: Float, longitude: Float) =
		try {
			networkService.getCurrentWeather(latitude, longitude)
		} catch (exception: IOException) {
			logDebugOut("NetworkRepository", "Failed to get current weather", exception)
			null
		}
	
	suspend fun getForecastWeather(latitude: Float, longitude: Float) =
		try {
			networkService.getForecastWeather(latitude, longitude)
		} catch (exception: IOException) {
			logDebugOut("NetworkRepository", "Failed to get forecast weather", exception)
			null
		}
}

fun logDebugOut(obj: String, message: String, param: Any) {
	val compiledMessage = "$obj. $message: $param"
	val tag = "WeatherForecast"
	
	if (param is Throwable)
		Log.e(tag, message, param)
	else
		Log.d(tag, compiledMessage)
}