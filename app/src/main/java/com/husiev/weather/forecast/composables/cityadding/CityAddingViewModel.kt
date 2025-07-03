package com.husiev.weather.forecast.composables.cityadding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.database.DatabaseRepository
import com.husiev.weather.forecast.database.entity.CityEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityAddingViewModel @Inject constructor(
	private val databaseRepository: DatabaseRepository,
): ViewModel() {
	
	fun selectAnotherCity(id: Int) {
		viewModelScope.launch(Dispatchers.IO) {
			var cities = emptyList<CityEntity>()
			
			databaseRepository.listOfCities.first { list ->
				val selected = list.find { it.selected }
				if (selected?.id == id) return@first true
				
				cities = list.map { city ->
					when(city.id) {
						id -> city.copy(selected = true)
						selected?.id -> city.copy(selected = false)
						else -> city
					}
				}
				true
			}
			
			databaseRepository.updateCities(cities)
		}
	}
	
}