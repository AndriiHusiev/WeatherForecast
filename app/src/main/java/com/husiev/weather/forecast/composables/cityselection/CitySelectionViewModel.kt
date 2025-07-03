package com.husiev.weather.forecast.composables.cityselection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.network.NetworkRepository
import com.husiev.weather.forecast.network.SearchResultUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitySelectionViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	@param:ApplicationContext private val context: Context,
): ViewModel() {
	
	private val _searchQuery = MutableStateFlow("")
	val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
	
	private val _searchResult = MutableStateFlow<SearchResultUiState>(SearchResultUiState.EmptyQuery)
	val searchResult: StateFlow<SearchResultUiState> = _searchResult.asStateFlow()
	
	fun onSearchTriggered(query: String) {
		viewModelScope.launch(Dispatchers.IO) {
			_searchResult.value = SearchResultUiState.Loading
			
			val result = networkRepository.getCitiesList(query)
			if (result is SearchResultUiState.LoadFailed) {
				if (result.cod == "400") {
					if (result.message == "Nothing to geocode") {
						_searchResult.value = SearchResultUiState.LoadFailed(
							message = context.resources.getString(R.string.empty_query)
						)
						return@launch
					} else if (result.message == "bad params") {
						_searchResult.value = SearchResultUiState.LoadFailed(
							message = context.resources.getString(R.string.invalid_query)
						)
						return@launch
					}
				}
			}
			
			_searchResult.value = result
		}
	}
	
	fun clearSearchResult() {
		_searchResult.value = SearchResultUiState.EmptyQuery
	}
	
	fun onSearchQueryChanged(query: String) {
		_searchQuery.value = query
	}
}