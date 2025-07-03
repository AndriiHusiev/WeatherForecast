package com.husiev.weather.forecast.composables.initialloading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.husiev.weather.forecast.R

@Composable
fun InitialLoadingScreen(
	viewModel: InitialViewModel = hiltViewModel(),
	onLoadEnd: (Int) -> Unit,
) {
	val initialLoadState by viewModel.initialLoadState.collectAsStateWithLifecycle()
	
	LaunchedEffect(initialLoadState) {
		when (val state = initialLoadState) {
			is InitialLoadState.Success -> onLoadEnd(state.cityId)
			
			InitialLoadState.Error,
			InitialLoadState.Empty -> onLoadEnd(0)
			
			InitialLoadState.Loading -> Unit
		}
	}
	
	// Display a loading indicator while fetching the ID
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		CircularProgressIndicator()
		Spacer(Modifier.height(dimensionResource(R.dimen.padding_medium)))
		Text("Loading selected city...")
	}
}