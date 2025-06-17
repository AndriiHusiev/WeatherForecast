package com.husiev.weather.forecast.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.husiev.weather.forecast.MainViewModel
import com.husiev.weather.forecast.composables.appbar.MainAppBar
import com.husiev.weather.forecast.navigation.NavState
import com.husiev.weather.forecast.navigation.WFNavHost
import com.husiev.weather.forecast.navigation.rememberNavState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
	modifier: Modifier = Modifier,
	mainViewModel: MainViewModel = hiltViewModel(),
	navState: NavState = rememberNavState()
) {
	val weather by mainViewModel.weather.collectAsStateWithLifecycle()
	
	Scaffold(
		modifier = modifier.fillMaxSize(),
		topBar = {
			MainAppBar(
				weather = weather,
				navState = navState,
			)
		}
	) { innerPadding ->
		Column(modifier = Modifier.padding(innerPadding)) {
			WFNavHost(
				navController = navState.navController,
				weather = weather,
				onChangeContent = navState::navigateToDestination,
				onCityItemClick = {
					mainViewModel.setCity(it)
					navState.navigateToDestination(Screen.MAIN)
				},
			)
		}
	}
}

enum class Screen {
	MAIN, SEL_CITY, SEL_DAY
}
