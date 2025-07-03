package com.husiev.weather.forecast.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.husiev.weather.forecast.composables.initialloading.InitialLoadingScreen

const val initialNavRoute = "initial_route"

fun NavController.navigateToInitial(navOptions: NavOptions? = null) {
	this.navigate(initialNavRoute, navOptions)
}

fun NavGraphBuilder.initialLoadingScreen(
	onLoadEnd: (Int) -> Unit,
) {
	composable(
		route = initialNavRoute,
	) {
		InitialLoadingScreen(
			onLoadEnd = onLoadEnd
		)
	}
}