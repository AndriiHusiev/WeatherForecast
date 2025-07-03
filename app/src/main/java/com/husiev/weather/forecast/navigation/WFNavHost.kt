package com.husiev.weather.forecast.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.husiev.weather.forecast.composables.main.WeatherInfo
import com.husiev.weather.forecast.network.NetworkCityInfo

private const val ANIM_DUR = 500
private const val FADE_DUR = 300

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WFNavHost(
	navState: NavState,
	weather: WeatherInfo,
	sharedTransitionScope: SharedTransitionScope,
	onCityItemClick: (NetworkCityInfo) -> Unit,
	modifier: Modifier = Modifier,
	startDestination: String = initialNavRoute,
) {
	val argKeys = navState.currentDestination?.arguments?.keys
	var title = ""
	if (argKeys != null &&  argKeys.isNotEmpty()) {
		navState.getIntDestArg(argKeys.first())?.let { index ->
			weather.fullForecast
				.firstOrNull { it.index == index }
				?.let { title = it.dateWeekDay }
		}
	}
	
	NavHost(
		navController = navState.navController,
		startDestination = startDestination,
		modifier = modifier,
		enterTransition = {
			when (targetState.destination.route) {
				citySelNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { -it } + fadeIn(tween(FADE_DUR))
				dayFullNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { it } + fadeIn(tween(FADE_DUR))
				else -> fadeIn(tween(FADE_DUR))
			}
		},
		exitTransition = {
			when (targetState.destination.route) {
				citySelNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { it } + fadeOut(tween(FADE_DUR))
				dayFullNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { -it } + fadeOut(tween(FADE_DUR))
				else -> fadeOut(tween(FADE_DUR))
			}
		},
		popEnterTransition = {
			when (initialState.destination.route) {
				citySelNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { it } + fadeIn(tween(FADE_DUR))
				dayFullNavRoute ->
					slideInHorizontally(tween(ANIM_DUR)) { -it } + fadeIn(tween(FADE_DUR))
				else -> fadeIn(tween(FADE_DUR))
			}
		},
		popExitTransition = {
			when (initialState.destination.route) {
				citySelNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { -it } + fadeOut(tween(FADE_DUR))
				dayFullNavRoute ->
					slideOutHorizontally(tween(ANIM_DUR)) { it } + fadeOut(tween(FADE_DUR))
				else -> fadeOut(tween(FADE_DUR))
			}
		}
	) {
		initialLoadingScreen(
			onLoadEnd = navState::navigateFromInitial
		)
		
		mainScreen(
			weather = weather,
			sharedTransitionScope = sharedTransitionScope,
			onChangeContent = navState::navigateToDestination,
			onActionClick = navState::navigateToDestination,
		)
		
		citySelScreen(
			onCityItemClick = onCityItemClick,
			onBack = navState::upPress
		)
		
		dayScreen(
			title = title,
			forecast = weather.fullForecast,
			onBack = navState::upPress,
		)
		
		cityAddScreen(
			cities = weather.preview,
			sharedTransitionScope = sharedTransitionScope,
			onFabClick = { navState.navigateToDestination(it) },
			onChangeContent = navState::navigateToDestination,
		)
	}
}