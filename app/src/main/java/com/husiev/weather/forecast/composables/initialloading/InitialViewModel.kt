package com.husiev.weather.forecast.composables.initialloading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.database.DatabaseRepository
import com.husiev.weather.forecast.network.logDebugOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialViewModel @Inject constructor(
	private val databaseRepository: DatabaseRepository,
): ViewModel() {
	
	private val _initialLoadState = MutableStateFlow<InitialLoadState>(InitialLoadState.Loading)
	val initialLoadState: StateFlow<InitialLoadState> = _initialLoadState.asStateFlow()
	
	init {
		viewModelScope.launch {
			try {
				val id = databaseRepository.getSelectedCityId()
				if (id != null)
					_initialLoadState.value = InitialLoadState.Success(id)
				else
					_initialLoadState.value = InitialLoadState.Empty
			} catch (exception: Exception) {
				logDebugOut("InitialViewModel", "Failed to get Selected City Id", exception)
				_initialLoadState.value = InitialLoadState.Error
			}
		}
	}
	
}