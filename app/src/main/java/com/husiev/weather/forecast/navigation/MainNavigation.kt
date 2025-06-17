package com.husiev.weather.forecast.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.husiev.weather.forecast.composables.MainContent
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.main.WeatherInfo

const val mainNavRoute = "main_route"

fun NavController.navigateToMain(navOptions: NavOptions? = null) {
	this.navigate(mainNavRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
	weather: WeatherInfo,
	onChangeContent: (Screen, Int) -> Unit,
) {
	composable(
		route = mainNavRoute,
	) {
		MainContent(
			weather = weather,
			onChangeContent = onChangeContent
		)
	}
}