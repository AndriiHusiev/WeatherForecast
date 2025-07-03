package com.husiev.weather.forecast.composables.initialloading

sealed class InitialLoadState {
	object Loading : InitialLoadState()
	data class Success(val cityId: Int) : InitialLoadState()
	object Empty : InitialLoadState()
	object Error : InitialLoadState()
}