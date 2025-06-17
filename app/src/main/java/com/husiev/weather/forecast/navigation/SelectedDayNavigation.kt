package com.husiev.weather.forecast.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.main.ForecastWeatherInfo
import com.husiev.weather.forecast.composables.main.SelectedDayContent

const val DAY_SINGLE_ARG = "single_day"
const val dayNavRoute = "day_route"
const val dayFullNavRoute = "$dayNavRoute/{$DAY_SINGLE_ARG}"

fun NavController.navigateToSelDay(singleArg: Int, navOptions: NavOptions? = null) {
	this.navigate("$dayNavRoute/$singleArg", navOptions)
}

fun NavGraphBuilder.dayScreen(
	forecast: List<ForecastWeatherInfo>,
	onChangeContent: (Screen) -> Unit,
) {
	composable(
		route = dayFullNavRoute,
		arguments = listOf(navArgument(DAY_SINGLE_ARG) { type = NavType.Companion.IntType })
	) { navBackStackEntry ->
		val singleArg = navBackStackEntry.arguments?.getInt(DAY_SINGLE_ARG)
		SelectedDayContent(
			singleDay = singleArg,
			forecast = forecast,
			onChangeContent = onChangeContent
		)
	}
}