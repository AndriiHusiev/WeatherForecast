package com.husiev.weather.forecast.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	
	private const val BASE_URL = "https://api.openweathermap.org/"
	
	private val json = Json { ignoreUnknownKeys = true }
	
	@Provides
	@Singleton
	fun provideNetworkService(): NetworkApiService {
		val retrofit: Retrofit = Retrofit.Builder()
			.addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
			.baseUrl(BASE_URL)
			.build()
		
		return retrofit.create(NetworkApiService::class.java)
	}
}