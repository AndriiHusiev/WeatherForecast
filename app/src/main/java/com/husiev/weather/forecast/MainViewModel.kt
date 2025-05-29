package com.husiev.weather.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.database.DatabaseRepository
import com.husiev.weather.forecast.database.entity.CityInfo
import com.husiev.weather.forecast.database.entity.asExternalModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val databaseRepository: DatabaseRepository
): ViewModel() {
	
	private var _screen: MutableStateFlow<Screen> = MutableStateFlow<Screen>(Screen.MAIN)
	val screen: StateFlow<Screen> = _screen.asStateFlow()
	
	fun onChangeContent(newScreen: Screen) {
		_screen.value = newScreen
	}
	
	val cityInfo: StateFlow<CityInfo?> = databaseRepository.listOfCities
		.map { list ->
			if (list.isNotEmpty()) {
				var name = ""
				var langTag = Locale.getDefault().language
				val item = list.first()
				databaseRepository.getLocalName(item.id, langTag).first { names ->
					name = if (names.isNotEmpty())
						names.first().name
					else
						item.name
					true
				}
				list.first().asExternalModel(name)
			} else null
		}
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5_000),
			initialValue = null
		)
}