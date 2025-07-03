package com.husiev.weather.forecast.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.Screen.*

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
			// Pop up to the main destination to
			// avoid building up a large stack of destinations
			// on the back stack as users select items
			if (destination == MAIN)
				popUpTo(mainNavFullRoute) { inclusive = true }
			// Avoid multiple copies of the same destination when
			// reselecting the same item
			launchSingleTop = true
		}
		
		when (destination) {
			INITIAL_LOADING -> navController.navigateToInitial(navOptions)
			MAIN -> navController.navigateToMain(singleArg, navOptions)
			SEL_CITY -> navController.navigateToCitySel(navOptions)
			SEL_DAY -> navController.navigateToSelDay(singleArg, navOptions)
			ADD_CITY -> navController.navigateToCityAdd(navOptions)
		}
	}
	
	fun navigateFromInitial(singleArg: Int) {
		val navOptions = navOptions {
			launchSingleTop = true
			// Remove the loading screen from the back stack
			popUpTo(initialNavRoute) { inclusive = true }
		}
		navController.navigateToMain(singleArg, navOptions)
	}
	
	fun upPress() {
		navController.navigateUp()
	}
	
	@Composable
	fun getIntDestArg(key: String): Int? =
		navController.currentBackStackEntryAsState().value?.arguments?.getInt(key)
	
	fun getPrevDestinationRoute(): String? =
		navController.previousBackStackEntry?.destination?.route
}