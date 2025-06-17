package com.husiev.weather.forecast.composables.cityselection

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.SearchListItem
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.network.SearchResultUiState
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitySelectionContent(
	onCityItemClick: (NetworkCityInfo) -> Unit = {},
	onChangeContent: (Screen) -> Unit = {},
	searchViewModel: CitySelectionViewModel = hiltViewModel(),
) {
	val state = rememberLazyListState()
	val searchQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
	val searchResult by searchViewModel.searchResult.collectAsStateWithLifecycle()
	
	Column(modifier = Modifier.fillMaxWidth()) {
		SearchBar(
			searchQuery = searchQuery,
			onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
			onSearchTriggered = searchViewModel::onSearchTriggered,
		)
		
		when (val searchState = searchResult) {
			SearchResultUiState.EmptyQuery -> Unit
			
			SearchResultUiState.LoadFailed -> FailScreen(modifier = Modifier.fillMaxSize())
			
			SearchResultUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
			
			is SearchResultUiState.Success -> {
				if (searchState.isEmpty()) {
					EmptySearchResultBody(searchQuery)
				} else {
					LazyColumn(state = state) {
						items(searchState.cities) { city ->
							SearchListItem(
								cityInfo = city,
								onClick = {
									onCityItemClick(it)
									searchViewModel.onSearchQueryChanged("")
									searchViewModel.clearSearchResult()
								}
							)
						}
					}
				}
			}
		}
	}
	
	BackHandler(
		enabled = true,
		onBack = { onChangeContent(Screen.MAIN) }
	)
}

@Composable
fun SearchBar(
	modifier: Modifier = Modifier,
	searchQuery: String = "",
	onSearchQueryChanged: (String) -> Unit = {},
	onSearchTriggered: (String) -> Unit = {},
) {
	val keyboardController = LocalSoftwareKeyboardController.current
	val focusManager = LocalFocusManager.current
	
	TextField(
		value = searchQuery,
		onValueChange = {
			if (!it.contains("\n")) {
				onSearchQueryChanged(it)
			}
		},
		modifier = modifier
			.fillMaxWidth()
			.padding(dimensionResource(R.dimen.padding_big)),
		placeholder = { Text(stringResource(R.string.search_city_text)) },
		leadingIcon = {
			Icon(
				imageVector = Icons.Filled.Search,
				contentDescription = stringResource(R.string.description_search_text),
			)
		},
		trailingIcon = {
			if (searchQuery.isNotEmpty()) {
				IconButton(
					onClick = { onSearchQueryChanged("") },
				) {
					Icon(
						imageVector = Icons.Filled.Close,
						contentDescription = stringResource(
							id = R.string.description_clear_search_text,
						),
					)
				}
			}
		},
		keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
		keyboardActions = KeyboardActions(onSearch = {
			onSearchTriggered(searchQuery)
			// Hide the keyboard after submitting the search
			keyboardController?.hide()
			//or hide keyboard
			focusManager.clearFocus()
			
		}),
		shape = RoundedCornerShape(dimensionResource(R.dimen.padding_extra_large)),
		colors = TextFieldDefaults.colors(
			focusedIndicatorColor = Color.Transparent,
			unfocusedIndicatorColor = Color.Transparent,
			disabledIndicatorColor = Color.Transparent,
		),
		maxLines = 1,
		singleLine = true,
	)
}

@Composable
fun EmptySearchResultBody(
	searchQuery: String,
) {
	val message = stringResource(id = R.string.search_result_not_found, searchQuery)
	val start = message.indexOf(searchQuery)
	Text(
		text = AnnotatedString(
			text = message,
			spanStyles = listOf(
				AnnotatedString.Range(
					SpanStyle(fontWeight = FontWeight.Bold),
					start = start,
					end = start + searchQuery.length,
				),
			),
		),
		style = MaterialTheme.typography.bodyLarge,
		textAlign = TextAlign.Center,
		modifier = Modifier.padding(
			horizontal = dimensionResource(R.dimen.padding_extra_large),
			vertical = dimensionResource(R.dimen.padding_large)
		),
	)
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
	val infiniteTransition = rememberInfiniteTransition(label = "rotation")
	val rotationAnim by infiniteTransition.animateFloat(
		initialValue = 0f,
		targetValue = 360f,
		animationSpec = infiniteRepeatable(
			animation = tween(3000, easing = LinearEasing),
			repeatMode = RepeatMode.Restart
		),
		label = "rotationAnimation"
	)
	
	Image(
		painter = painterResource(R.drawable.loading_img),
		contentDescription = stringResource(R.string.description_loading_content),
		contentScale = ContentScale.Crop,
		modifier = modifier
			.graphicsLayer {
				rotationZ = rotationAnim
			}
	)
}

@Composable
fun FailScreen(modifier: Modifier = Modifier) {
	Column(
		modifier = modifier,
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			painter = painterResource(R.drawable.ic_connection_error),
			contentDescription = stringResource(R.string.description_loading_failed)
		)
		Text(
			text = stringResource(R.string.search_loading_failed),
			modifier = Modifier.padding(
				horizontal = dimensionResource(R.dimen.padding_extra_large),
				vertical = dimensionResource(R.dimen.padding_big)
			),
			textAlign = TextAlign.Center
		)
	}
}

@Preview(showBackground = true)
@Composable
fun SearchContentPreview() {
	WeatherForecastTheme {
		Surface {
			Column {
				SearchBar(searchQuery = "Kyiv")
			}
		}
	}
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Composable
fun LoadingScreenPreview() {
	WeatherForecastTheme {
		Column {
			LoadingScreen(modifier = Modifier.fillMaxSize())
		}
	}
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Composable
fun EmptySearchResultBodyPreview() {
	WeatherForecastTheme {
		Column {
			EmptySearchResultBody("asdf")
		}
	}
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Composable
fun FailScreenScreenPreview() {
	WeatherForecastTheme {
		Column {
			FailScreen(modifier = Modifier.fillMaxSize())
		}
	}
}