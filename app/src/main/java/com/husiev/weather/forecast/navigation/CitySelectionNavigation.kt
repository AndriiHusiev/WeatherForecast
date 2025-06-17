package com.husiev.weather.forecast.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.cityselection.CitySelectionContent
import com.husiev.weather.forecast.network.NetworkCityInfo

const val cityNavRoute = "city_route"

fun NavController.navigateToCitySel(navOptions: NavOptions? = null) {
	this.navigate(cityNavRoute, navOptions)
}

fun NavGraphBuilder.cityScreen(
	onCityItemClick: (NetworkCityInfo) -> Unit,
	onChangeContent: (Screen) -> Unit,
) {
	composable(
		route = cityNavRoute,
	) {
		CitySelectionContent(
			onCityItemClick = onCityItemClick,
			onChangeContent = onChangeContent
		)
	}
}