package com.husiev.weather.forecast.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.husiev.weather.forecast.composables.MainContent
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.main.WeatherInfo

const val CITY_ID_ARG = "city_id"
const val mainNavRoute = "main_route"
const val mainNavFullRoute = "$mainNavRoute/{$CITY_ID_ARG}"

fun NavController.navigateToMain(singleArg: Int, navOptions: NavOptions? = null) {
	this.navigate("$mainNavRoute/$singleArg", navOptions)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.mainScreen(
	weather: WeatherInfo,
	sharedTransitionScope: SharedTransitionScope,
	onChangeContent: (Screen, Int) -> Unit,
	onActionClick: (Screen) -> Unit,
) {
	composable(
		route = mainNavFullRoute,
		arguments = listOf(navArgument(CITY_ID_ARG) { type = NavType.Companion.IntType })
	) { navBackStackEntry ->
		val selectedId = navBackStackEntry.arguments?.getInt(CITY_ID_ARG)!!
		MainContent(
			selectedId = selectedId,
			weather = weather,
			sharedTransitionScope = sharedTransitionScope,
			animatedContentScope = this@composable,
			onChangeContent = onChangeContent,
			onActionClick = onActionClick,
		)
	}
}