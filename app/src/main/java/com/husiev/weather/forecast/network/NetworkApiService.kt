package com.husiev.weather.forecast.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiService {
	
	/**
	 * Returns locations while working with geographic names.
	 * @param city City name, state code (only for the US) and country code divided by comma.
	 * Please use ISO 3166 country codes.
	 * @param appId Application ID.
	 * @param limit Number of the locations in the API response (up to 5 results can be returned
	 * in the API response).
	 */
	@GET("geo/1.0/direct")
	suspend fun getCities(
		@Query("q") city: String,
		@Query("limit") limit: Int = 5,
		@Query("appid") appId: String = "a0379a533a0c2397d01c5dfb7386e2e2",
	): Response<List<NetworkCityInfo>>
	
	/**
	 * Returns current weather data for any location on Earth.
	 * @param latitude Latitude.
	 * @param longitude Longitude.
	 * @param appId Application ID.
	 * @param units Units of measurement. "standard", "metric" and "imperial" units are available.
	 */
	@GET("data/2.5/weather")
	suspend fun getCurrentWeather(
		@Query("lat") latitude: Float,
		@Query("lon") longitude: Float,
		@Query("appid") appId: String = "a0379a533a0c2397d01c5dfb7386e2e2",
		@Query("units") units: String = "metric",
	): NetworkTodayInfo
	
	/**
	 * Returns 5 day forecast data with 3-hour step.
	 * @param latitude Latitude.
	 * @param longitude Longitude.
	 * @param appId Application ID.
	 * @param units Units of measurement. "standard", "metric" and "imperial" units are available.
	 */
	@GET("data/2.5/forecast")
	suspend fun getForecastWeather(
		@Query("lat") latitude: Float,
		@Query("lon") longitude: Float,
		@Query("appid") appId: String = "a0379a533a0c2397d01c5dfb7386e2e2",
		@Query("units") units: String = "metric",
	): NetworkForecastInfo
}