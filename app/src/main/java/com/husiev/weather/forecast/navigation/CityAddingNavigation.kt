package com.husiev.weather.forecast.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.cityadding.CityAddingContent
import com.husiev.weather.forecast.composables.cityadding.PreviewCityInfo

const val cityAddNavRoute = "city_add_route"

fun NavController.navigateToCityAdd(navOptions: NavOptions? = null) {
	this.navigate(cityAddNavRoute, navOptions)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.cityAddScreen(
	cities: List<PreviewCityInfo>,
	sharedTransitionScope: SharedTransitionScope,
	onChangeContent: (Screen, Int) -> Unit,
	onFabClick: (Screen) -> Unit = {},
) {
	composable(
		route = cityAddNavRoute,
	) { navBackStackEntry ->
		CityAddingContent(
			cities = cities,
			sharedTransitionScope = sharedTransitionScope,
			animatedContentScope = this@composable,
			onChangeContent = onChangeContent,
			onFabClick = onFabClick,
		)
	}
}