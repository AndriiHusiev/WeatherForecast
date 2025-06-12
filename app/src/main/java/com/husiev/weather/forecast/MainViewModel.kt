package com.husiev.weather.forecast

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.composables.Screen
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.collections.first

@HiltViewModel
class MainViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	private val databaseRepository: DatabaseRepository,
	@ApplicationContext private val context: Context,
): ViewModel() {
	
	init {
		viewModelScope.launch(Dispatchers.IO) {
			loadWeather()
		}
	}
	
	private var _screen: MutableStateFlow<Screen> = MutableStateFlow<Screen>(Screen.MAIN)
	val screen: StateFlow<Screen> = _screen.asStateFlow()
	
	fun onChangeContent(newScreen: Screen) {
		_screen.value = newScreen
	}
	
	val weather: StateFlow<WeatherInfo> =
		combine(
			databaseRepository.listOfCities,
			databaseRepository.getCurrentWeather(),
			databaseRepository.getForecastWeather()
		) { cities, today, forecast ->
			WeatherInfo(
				getCityInfo(cities, databaseRepository),
				today.asExternalModel(),
				forecast.asBriefModel(context),
				forecast.asFullModel(),
			)
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = WeatherInfo()
		)
	
	fun setCity(city: NetworkCityInfo) {
		viewModelScope.launch(Dispatchers.IO) {
			databaseRepository.listOfCities.first {
				if (it.isNotEmpty())
					databaseRepository.replaceCity(it.first(), city)
				else
					databaseRepository.addCity(city)
				true
			}
			loadWeather()
		}
	}
	
	suspend fun loadWeather() {
		val info = getCityEntity(databaseRepository)
		if (info != null) {
			val today = networkRepository.getCurrentWeather(info.lat, info.lon)
			val forecast = networkRepository.getForecastWeather(info.lat, info.lon)
			if (today != null)
				databaseRepository.saveCurrentWeather(today, info.id)
			if (forecast != null)
				databaseRepository.saveForecastWeather(forecast, info.id)
		}
	}
}

suspend fun getCityInfo(
	list: List<CityEntity>,
	db: DatabaseRepository,
): CityInfo? {
	var info: CityInfo? = null
	
	if (list.isNotEmpty()) {
		var name = ""
		var langTag = Locale.getDefault().language
		val item = list.first()
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

suspend fun getCityEntity(db: DatabaseRepository): CityEntity? {
	var item: CityEntity? = null
	db.listOfCities.first {
		if (it.isNotEmpty()) {
			item = it.first()
		}
		true
	}
	
	return item
}