package com.husiev.weather.forecast.composables.cityselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.database.DatabaseRepository
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.NetworkRepository
import com.husiev.weather.forecast.network.SearchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySelectionViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	private val databaseRepository: DatabaseRepository
): ViewModel() {
	
	private val _searchQuery = MutableStateFlow<String>("")
	val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
	
	private val _searchResult = MutableStateFlow<SearchResultUiState>(SearchResultUiState.EmptyQuery)
	val searchResult: StateFlow<SearchResultUiState> = _searchResult.asStateFlow()
	
	fun setCity(city: NetworkCityInfo) {
		viewModelScope.launch(Dispatchers.IO) {
			databaseRepository.listOfCities.first {
				if (it.isNotEmpty())
					databaseRepository.replaceCity(it.first(), city)
				else
					databaseRepository.addCity(city)
				true
			}
		}
		onSearchQueryChanged("")
		clearSearchResult()
	}
	
	fun onSearchTriggered(query: String) {
		viewModelScope.launch(Dispatchers.IO) {
			networkRepository.getCitiesList(query).collect {
				_searchResult.value = it
			}
		}
	}
	
	fun clearSearchResult() {
		_searchResult.value = SearchResultUiState.EmptyQuery
	}
	
	fun onSearchQueryChanged(query: String) {
		_searchQuery.value = query
	}
}