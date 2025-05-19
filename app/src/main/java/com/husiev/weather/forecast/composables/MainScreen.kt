package com.husiev.weather.forecast.composables

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.husiev.weather.forecast.MainViewModel
import com.husiev.weather.forecast.network.SearchResultUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
	modifier: Modifier = Modifier,
	searchViewModel: MainViewModel = hiltViewModel(),
) {
	var selectedScreen by rememberSaveable { mutableStateOf(Screen.MAIN) }
	val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
	val searchResult by searchViewModel.searchResult.collectAsStateWithLifecycle()
	
	Crossfade(
		targetState = selectedScreen,
		modifier = modifier,
	) {
		Scaffold(
			modifier = modifier.fillMaxSize(),
			topBar = {
				TopAppBar(
					screen = it,
					onNavigationClick = { selectedScreen = Screen.MAIN },
				)
			}
		) { innerPadding ->
			Column(modifier = Modifier.padding(innerPadding)) {
				when(it) {
					Screen.MAIN -> MainContent(
						onChangeContent = {
							selectedScreen = it
						},
					)
					Screen.SEL_CITY -> CitySelectionContent(
						searchState = searchResult,
						searchQuery = searchQuery,
						onChangeContent = {
							searchViewModel.onSearchQueryChanged("")
							searchViewModel.searchResult.value = SearchResultUiState.EmptyQuery
							selectedScreen = it
						},
						onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
						onSearchTriggered = searchViewModel::onSearchTriggered,
						onSelectCity = {
							searchViewModel.cityInfo = it
							searchViewModel.onSearchQueryChanged("")
							searchViewModel.searchResult.value = SearchResultUiState.EmptyQuery
							selectedScreen = Screen.MAIN
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
