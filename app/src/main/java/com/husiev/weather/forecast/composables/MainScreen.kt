package com.husiev.weather.forecast.composables

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.husiev.weather.forecast.MainViewModel
import com.husiev.weather.forecast.navigation.NavState
import com.husiev.weather.forecast.navigation.WFNavHost
import com.husiev.weather.forecast.navigation.cityAddNavRoute
import com.husiev.weather.forecast.navigation.rememberNavState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
	mainViewModel: MainViewModel = hiltViewModel(),
	navState: NavState = rememberNavState()
) {
	val weather by mainViewModel.weather.collectAsStateWithLifecycle()
	val lifecycleOwner = LocalLifecycleOwner.current
	
	DisposableEffect(lifecycleOwner) {
		val observer = LifecycleEventObserver { _, event ->
			if (event == Lifecycle.Event.ON_RESUME) {
				mainViewModel.refreshDataOnResume()
			}
		}
		lifecycleOwner.lifecycle.addObserver(observer)
		
		onDispose {
			// Remove the observer to prevent memory leaks
			lifecycleOwner.lifecycle.removeObserver(observer)
		}
	}
	
	SharedTransitionLayout {
		WFNavHost(
			navState = navState,
			weather = weather,
			sharedTransitionScope = this@SharedTransitionLayout,
			onCityItemClick = {
				val prev = navState.getPrevDestinationRoute()
				mainViewModel.setCity(it, prev == cityAddNavRoute)
				navState.upPress()
			},
		)
	}
}

enum class Screen {
	INITIAL_LOADING, MAIN, SEL_CITY, SEL_DAY, ADD_CITY
}
