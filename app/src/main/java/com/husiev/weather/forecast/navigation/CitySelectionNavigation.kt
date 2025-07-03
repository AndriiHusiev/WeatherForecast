package com.husiev.weather.forecast.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.husiev.weather.forecast.composables.cityselection.CitySelectionContent
import com.husiev.weather.forecast.network.NetworkCityInfo

const val citySelNavRoute = "city_route"

fun NavController.navigateToCitySel(navOptions: NavOptions? = null) {
	this.navigate(citySelNavRoute, navOptions)
}

fun NavGraphBuilder.citySelScreen(
	onCityItemClick: (NetworkCityInfo) -> Unit,
	onBack: () -> Unit,
) {
	composable(
		route = citySelNavRoute,
	) {
		CitySelectionContent(
			onCityItemClick = onCityItemClick,
			onBack = onBack
		)
	}
}