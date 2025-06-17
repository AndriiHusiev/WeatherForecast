package com.husiev.weather.forecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.Screen.MAIN
import com.husiev.weather.forecast.composables.Screen.SEL_CITY
import com.husiev.weather.forecast.composables.Screen.SEL_DAY

@Composable
fun rememberNavState(
	navController: NavHostController = rememberNavController()
): NavState = remember(navController) {
	NavState(navController)
}

class NavState(
	val navController: NavHostController
) {
	
	val currentDestination: NavDestination?
		@Composable get() = navController.currentBackStackEntryAsState().value?.destination
	
	fun navigateToDestination(destination: Screen, singleArg: Int = 0) {
		val navOptions = navOptions {
			// Pop up to the start destination of the graph to
			// avoid building up a large stack of destinations
			// on the back stack as users select items
			popUpTo(navController.graph.findStartDestination().id) {
				saveState = true
			}
			// Avoid multiple copies of the same destination when
			// reselecting the same item
			launchSingleTop = true
			
			if (destination != SEL_DAY)
				// Restore state when reselecting a previously selected item
				restoreState = true
		}
		
		when (destination) {
			MAIN -> navController.navigateToMain(navOptions)
			SEL_CITY -> navController.navigateToCitySel(navOptions)
			SEL_DAY -> navController.navigateToSelDay(singleArg, navOptions)
		}
	}
	
	@Composable
	fun getIntDestArg(key: String): Int? =
		navController.currentBackStackEntryAsState().value?.arguments?.getInt(key)
}