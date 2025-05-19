package com.husiev.weather.forecast.network

sealed interface SearchResultUiState {
	
	object Loading : SearchResultUiState
	
	/**
	 * The state query is empty or too short. To distinguish the state between the
	 * (initial state or when the search query is cleared) vs the state where no search
	 * result is returned, explicitly define the empty query state.
	 */
	object EmptyQuery : SearchResultUiState
	
	object LoadFailed : SearchResultUiState
	
	data class Success(
		val cities: List<NetworkCityInfo> = emptyList<NetworkCityInfo>(),
	) : SearchResultUiState {
		fun isEmpty(): Boolean = cities.isEmpty()
	}
	
}