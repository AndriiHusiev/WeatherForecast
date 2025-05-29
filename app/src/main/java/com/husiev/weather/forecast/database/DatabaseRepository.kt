package com.husiev.weather.forecast.database

import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.asEntity
import com.husiev.weather.forecast.network.asLocalNamesEntity
import com.husiev.weather.forecast.network.logDebugOut
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
		logDebugOut("DatabaseRepository", "Calendar.getInstance.time.time", id)
		database.citiesDao().insert(city.asEntity(id))
		database.localNamesDao().insertAll(city.asLocalNamesEntity(id))
	}
	
	suspend fun replaceCity(oldCity: CityEntity, newCity: NetworkCityInfo) {
		database.citiesDao().delete(oldCity)
		database.citiesDao().insert(newCity.asEntity(oldCity.id))
		database.localNamesDao().insertAll(newCity.asLocalNamesEntity(oldCity.id))
	}
	
	suspend fun getCity(lat: Float, lon: Float) = database.citiesDao().loadCity(lat, lon)
	
	suspend fun getLocalName(cityId: Int, locale: String) =
		database.localNamesDao().loadLocalName(cityId, locale)
	
}