package com.husiev.weather.forecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.NetworkRepository
import com.husiev.weather.forecast.network.SearchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_QUERY = "searchQuery"

@HiltViewModel
class MainViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	private val savedStateHandle: SavedStateHandle,
): ViewModel() {
	
	var cityInfo: NetworkCityInfo? = null
	
	var searchQuery: StateFlow<String> = savedStateHandle.getStateFlow(SEARCH_QUERY, "")
	
	var searchResult = MutableStateFlow<SearchResultUiState>(SearchResultUiState.EmptyQuery)
	
	fun onSearchTriggered(query: String) {
		viewModelScope.launch(Dispatchers.IO) {
			networkRepository.getCitiesList(query).collect {
				searchResult.value = it
			}
		}
	}
	
	fun onSearchQueryChanged(query: String) {
		savedStateHandle[SEARCH_QUERY] = query
	}
}