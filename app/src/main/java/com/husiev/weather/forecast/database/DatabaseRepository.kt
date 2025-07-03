package com.husiev.weather.forecast.database

import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.NetworkForecastInfo
import com.husiev.weather.forecast.network.NetworkTodayInfo
import com.husiev.weather.forecast.network.asEntity
import com.husiev.weather.forecast.network.asLocalNamesEntity
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
	private val database: AppDatabase
) {
	
	val listOfCities: Flow<List<CityEntity>> = database.citiesDao().loadCitiesList()
	
	suspend fun addCity(city: NetworkCityInfo, select: Boolean) {
		val id = Calendar.getInstance().time.time.toInt()
		database.citiesDao().insert(city.asEntity(id, select))
		database.localNamesDao().insertAll(city.asLocalNamesEntity(id))
	}
	
	suspend fun replaceCity(oldCity: CityEntity, newCity: NetworkCityInfo) {
		database.citiesDao().insert(newCity.asEntity(oldCity.id, oldCity.selected))
		database.localNamesDao().insertAll(newCity.asLocalNamesEntity(oldCity.id))
	}
	
	suspend fun getSelectedCityId() = database.citiesDao().getSelectedCityId()
	
	suspend fun updateCities(cities: List<CityEntity>) =
		database.citiesDao().updateCities(cities)
	
	fun getLocalName(cityId: Int, locale: String) =
		database.localNamesDao().loadLocalName(cityId, locale)
	
	suspend fun saveCurrentWeather(currentWeather: NetworkTodayInfo, cityId: Int) =
		database.currentWeatherDao().insertOrReplace(currentWeather.asEntity(cityId))
	
	fun getCurrentWeather() = database.currentWeatherDao().loadCurrentWeather()
	
	suspend fun saveForecastWeather(forecastWeather: NetworkForecastInfo, cityId: Int) =
		database.forecastWeatherDao().insertOrReplace(forecastWeather.asEntity(cityId))
	
	fun getForecastWeather() = database.forecastWeatherDao().loadForecastWeather()
	
	fun getPreviewWeather() = database.previewWeatherDao().loadPreviewWeather()
	
}