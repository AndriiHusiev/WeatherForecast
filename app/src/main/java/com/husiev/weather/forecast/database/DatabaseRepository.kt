package com.husiev.weather.forecast.database

import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.network.NetworkCityInfo
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
	
	suspend fun addCity(city: NetworkCityInfo) {
		val id = Calendar.getInstance().time.time.toInt()
		database.citiesDao().insert(city.asEntity(id))
		database.localNamesDao().insertAll(city.asLocalNamesEntity(id))
	}
	
	suspend fun replaceCity(oldCity: CityEntity, newCity: NetworkCityInfo) {
		database.citiesDao().delete(oldCity)
		val id = Calendar.getInstance().time.time.toInt()
		database.citiesDao().insert(newCity.asEntity(id))
		database.localNamesDao().insertAll(newCity.asLocalNamesEntity(id))
	}
	
	fun getLocalName(cityId: Int, locale: String) =
		database.localNamesDao().loadLocalName(cityId, locale)
	
	suspend fun saveCurrentWeather(currentWeather: NetworkTodayInfo, cityId: Int) =
		database.currentWeatherDao().insertOrReplace(currentWeather.asEntity(cityId))
	
	fun getCurrentWeather() = database.currentWeatherDao().loadCurrentWeather()
	
}