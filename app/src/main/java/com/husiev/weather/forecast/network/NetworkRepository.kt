package com.husiev.weather.forecast.network

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
	private val networkService: NetworkApiService,
) {
	
	suspend fun getCitiesList(search: String): SearchResultUiState {
		return try {
			val response = networkService.getCities(search)
			
			if (response.isSuccessful) {
				// HTTP 2xx response
				response.body()?.let {
					SearchResultUiState.Success(it)
				} ?: SearchResultUiState.LoadFailed(
					message = "Response body is null for a successful request"
				)
			} else {
				val errorBodyString = response.errorBody()?.string()
				val errorResponse = try {
					// Attempt to parse the error body if it's structured JSON
					Gson().fromJson(errorBodyString, SearchResultUiState.LoadFailed::class.java)
						?: SearchResultUiState.LoadFailed(response.code().toString(), response.message())
				} catch (e: Exception) {
					// If parsing fails or errorBodyString is null, use a generic message
					SearchResultUiState.LoadFailed(response.code().toString(), response.message())
				}
				
				logDebugOut(
					"NetworkRepository",
					"Server error " + errorResponse.cod,
					errorResponse.message ?: "n/a"
				)
				
				errorResponse
			}
		} catch (exception: IOException) {
			logDebugOut("NetworkRepository", "Failed to get list of cities", exception)
			SearchResultUiState.LoadFailed(message = exception.message)
		} catch (exception: HttpException) {
			logDebugOut("NetworkRepository", "Failed to get list of cities", exception)
			SearchResultUiState.LoadFailed(message = exception.message)
		} catch (exception: Exception) {
			// Catch any other exceptions (network issues, JSON parsing errors for success body, etc.)
			logDebugOut("NetworkRepository", "Failed to get list of cities", exception)
			SearchResultUiState.LoadFailed(message = exception.message)
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