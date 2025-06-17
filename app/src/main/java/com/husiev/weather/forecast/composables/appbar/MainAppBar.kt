package com.husiev.weather.forecast.composables.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.main.WeatherInfo
import com.husiev.weather.forecast.navigation.NavState
import com.husiev.weather.forecast.navigation.cityNavRoute
import com.husiev.weather.forecast.navigation.dayFullNavRoute
import com.husiev.weather.forecast.navigation.mainNavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
	weather: WeatherInfo,
	navState: NavState,
	modifier: Modifier = Modifier,
) {
	var title = stringResource(R.string.app_name)
	var navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack
	var actionIcon: ImageVector? = null
	
	var navigationIconContentDescription: String? = stringResource(R.string.back)
	var actionIconContentDescription: String? = null
	
	var onNavigationClick: () -> Unit = { navState.navigateToDestination(Screen.MAIN) }
	var onActionClick: () -> Unit = {}
	
	navState.currentDestination?.let {
		when(it.route) {
			
			mainNavRoute -> {
				weather.cityInfo?.let { title = it.name }
				navigationIcon = Icons.Filled.Search
				navigationIconContentDescription = stringResource(R.string.select_city)
				onNavigationClick = {
					navState.navigateToDestination(Screen.SEL_CITY)
				}
			}
			
			cityNavRoute -> {
				title = stringResource(R.string.select_city)
			}
			
			dayFullNavRoute -> {
				val argKeys = it.arguments.keys
				if (argKeys.isNotEmpty()) {
					navState.getIntDestArg(argKeys.first())?.let { index ->
						weather.fullForecast
							.firstOrNull { it.index == index }
							?.let { title = it.dateWeekDay }
					}
				}
			}
			
		}
	}
	
	TopAppBar(
		title = title,
		modifier = modifier,
		navigationIcon = navigationIcon,
		actionIcon = actionIcon,
		navigationIconContentDescription = navigationIconContentDescription,
		actionIconContentDescription = actionIconContentDescription,
		onNavigationClick = onNavigationClick,
		onActionClick = onActionClick
	)
}