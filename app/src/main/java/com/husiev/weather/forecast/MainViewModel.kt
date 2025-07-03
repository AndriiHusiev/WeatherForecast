package com.husiev.weather.forecast

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.composables.cityadding.asExternalModel
import com.husiev.weather.forecast.composables.main.CityInfo
import com.husiev.weather.forecast.composables.main.WeatherInfo
import com.husiev.weather.forecast.database.DatabaseRepository
import com.husiev.weather.forecast.database.entity.CityEntity
import com.husiev.weather.forecast.database.entity.asBriefModel
import com.husiev.weather.forecast.database.entity.asExternalModel
import com.husiev.weather.forecast.database.entity.asFullModel
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	private val databaseRepository: DatabaseRepository,
	@param:ApplicationContext private val context: Context,
): ViewModel() {
	
	fun refreshDataOnResume() {
		viewModelScope.launch(Dispatchers.IO) {
			loadWeather()
		}
	}
	
	val weather: StateFlow<WeatherInfo> =
		combine(
			databaseRepository.listOfCities,
			databaseRepository.getCurrentWeather(),
			databaseRepository.getForecastWeather(),
			databaseRepository.getPreviewWeather()
		) { cities, today, forecast, preview ->
			val selectedCity = cities.firstOrNull { it.selected }
			val cityId = selectedCity?.id ?: 0
			val subListForecast = forecast.filter { it.cityId == cityId }
			
			WeatherInfo(
				getCityInfo(selectedCity, databaseRepository),
				today.firstOrNull { it.cityId == cityId }.asExternalModel(),
				subListForecast.asBriefModel(context),
				subListForecast.asFullModel(),
				preview.asExternalModel(context)
			)
		}
			.flowOn(Dispatchers.Default)
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5_000),
				initialValue = WeatherInfo()
			)
	
	fun setCity(city: NetworkCityInfo, addNewCity: Boolean) {
		viewModelScope.launch(Dispatchers.IO) {
			databaseRepository.listOfCities.first { list ->
				if (list.isEmpty() || addNewCity)
					databaseRepository.addCity(city, !addNewCity)
				else
					databaseRepository.replaceCity(list.first { it.selected }, city)
				true
			}
			loadWeather()
		}
	}
	
	suspend fun loadWeather() {
		databaseRepository.listOfCities.first { cities ->
			if (cities.isEmpty()) return@first true
			
			cities.forEach { city ->
				val today = networkRepository.getCurrentWeather(city.lat, city.lon)
				val forecast = networkRepository.getForecastWeather(city.lat, city.lon)
				if (today != null)
					databaseRepository.saveCurrentWeather(today, city.id)
				if (forecast != null)
					databaseRepository.saveForecastWeather(forecast, city.id)
			}
			
			true
		}
	}
}

suspend fun getCityInfo(
	city: CityEntity?,
	db: DatabaseRepository,
): CityInfo? {
	var info: CityInfo? = null
	
	city?.let { item ->
		var name = ""
		val langTag = Locale.getDefault().language
		db.getLocalName(item.id, langTag).first { names ->
			name = if (names.isNotEmpty())
				names.first().name
			else
				item.name
			true
		}
		info = item.asExternalModel(name)
	}
	
	return info
}