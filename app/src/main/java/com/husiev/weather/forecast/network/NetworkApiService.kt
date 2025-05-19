package com.husiev.weather.forecast.network

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
	): List<NetworkCityInfo>
	
}