package com.husiev.weather.forecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val networkRepository: NetworkRepository,
	private val savedStateHandle: SavedStateHandle,
): ViewModel() {
	
	private var _screen: MutableStateFlow<Screen> = MutableStateFlow<Screen>(Screen.MAIN)
	val screen: StateFlow<Screen> = _screen.asStateFlow()
	
	fun onChangeContent(newScreen: Screen) {
		_screen.value = newScreen
	}
}