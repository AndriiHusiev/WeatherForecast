package com.husiev.weather.forecast.composables

import androidx.compose.animation.Crossfade
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
import com.husiev.weather.forecast.composables.cityselection.CitySelectionContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
	modifier: Modifier = Modifier,
	mainViewModel: MainViewModel = hiltViewModel(),
) {
	val selectedScreen by mainViewModel.screen.collectAsStateWithLifecycle()
	val weather by mainViewModel.weather.collectAsStateWithLifecycle()
	
	Crossfade(
		targetState = selectedScreen,
		modifier = modifier,
	) {
		Scaffold(
			modifier = modifier.fillMaxSize(),
			topBar = {
				TopAppBar(
					screen = it,
					name = weather.cityInfo?.name ?: "",
					onNavigationClick = { mainViewModel.onChangeContent(
						if (selectedScreen == Screen.MAIN)
							Screen.SEL_CITY
						else
							Screen.MAIN
					)},
				)
			}
		) { innerPadding ->
			Column(modifier = Modifier.padding(innerPadding)) {
				when(it) {
					Screen.MAIN -> MainContent(
						weather = weather,
						onChangeContent = mainViewModel::onChangeContent,
					)
					Screen.SEL_CITY -> CitySelectionContent(
						onChangeContent = mainViewModel::onChangeContent,
						onItemClick = {
							mainViewModel.setCity(it)
							mainViewModel.onChangeContent(Screen.MAIN)
						}
					)
				}
			}
		}
	}
}

enum class Screen {
	MAIN, SEL_CITY
}
