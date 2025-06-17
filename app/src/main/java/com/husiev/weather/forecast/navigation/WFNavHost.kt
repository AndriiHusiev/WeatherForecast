package com.husiev.weather.forecast.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.main.WeatherInfo
import com.husiev.weather.forecast.network.NetworkCityInfo

private const val ANIM_DUR = 400
private const val FADE_DUR = 300

@Composable
fun WFNavHost(
	navController: NavHostController,
	weather: WeatherInfo,
	onChangeContent: (Screen, Int) -> Unit,
	onCityItemClick: (NetworkCityInfo) -> Unit,
	modifier: Modifier = Modifier,
	startDestination: String = mainNavRoute,
) {
	NavHost(
		navController = navController,
		startDestination = startDestination,
		modifier = modifier,
		enterTransition = {
			when (targetState.destination.route) {
				cityNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { -it } + fadeIn(tween(FADE_DUR))
				dayFullNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { it } + fadeIn(tween(FADE_DUR))
				else -> fadeIn(tween(FADE_DUR))
			}
		},
		exitTransition = {
			when (targetState.destination.route) {
				cityNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { it } + fadeOut(tween(FADE_DUR))
				dayFullNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { -it } + fadeOut(tween(FADE_DUR))
				else -> fadeOut(tween(FADE_DUR))
			}
		},
		popEnterTransition = {
			when (initialState.destination.route) {
				cityNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { it } + fadeIn(tween(FADE_DUR))
				dayFullNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { -it } + fadeIn(tween(FADE_DUR))
				else -> fadeIn(tween(FADE_DUR))
			}
		},
		popExitTransition = {
			when (initialState.destination.route) {
				cityNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { -it } + fadeOut(tween(FADE_DUR))
				dayFullNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { it } + fadeOut(tween(FADE_DUR))
				else -> fadeOut(tween(FADE_DUR))
			}
		}
	) {
		mainScreen(
			weather = weather,
			onChangeContent = onChangeContent
		)
		
		cityScreen(
			onCityItemClick = onCityItemClick,
			onChangeContent = { onChangeContent(it, 0) }
		)
		
		dayScreen(
			forecast = weather.fullForecast,
			onChangeContent = { onChangeContent(it, 0) }
		)
	}
}