package com.husiev.weather.forecast.composables.cityadding

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.EmptyMainContent
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.composables.appbar.TopAppBar
import com.husiev.weather.forecast.composables.main.WeatherCard
import com.husiev.weather.forecast.database.entity.codeToResId
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CityAddingContent(
	cities: List<PreviewCityInfo>,
	sharedTransitionScope: SharedTransitionScope,
	animatedContentScope: AnimatedContentScope,
	onChangeContent: (Screen, Int) -> Unit,
	onFabClick: (Screen) -> Unit = {},
	viewModel: CityAddingViewModel = hiltViewModel(),
) {
	Surface(
		modifier = Modifier.fillMaxSize(),
		color = Color.White
	) {
		with(animatedContentScope) {
			with(sharedTransitionScope) {
				Scaffold(
					topBar = {
						TopAppBar(
							title = stringResource(R.string.list_of_cities),
							modifier = Modifier.zIndex(100f),
							colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
								containerColor = Color.Transparent,
								titleContentColor = MaterialTheme.colorScheme.surface
							),
						)
					},
					floatingActionButton = {
						FloatingActionButton(
							onClick = { onFabClick(Screen.SEL_CITY) },
							modifier = Modifier
								.renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
								.animateEnterExit(),
							containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
							contentColor = MaterialTheme.colorScheme.onSurface
						) {
							Icon(Icons.Filled.Add, stringResource(R.string.add_new_city))
						}
					},
					containerColor = Color.Transparent
				) { innerPadding ->
					if (cities.isEmpty())
						EmptyMainContent(
							modifier = Modifier.padding(innerPadding),
							fontColor = Color.Black
						)
					else
						LazyColumn(
							modifier = Modifier
								.fillMaxWidth()
								.padding(innerPadding)
								.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
							state = rememberLazyListState(),
							verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
						) {
							items(cities) { city ->
								CityAddingItem(
									cityName = city.name,
									temperature = city.temperature,
									weatherDesc = city.weatherDesc,
									weatherIcon = city.weatherIcon,
									modifier = Modifier
										.sharedBounds(
											sharedContentState = rememberSharedContentState(
												key = city.id,
											),
											animatedVisibilityScope = animatedContentScope,
											exit = fadeOut(tween(500)),
											enter = fadeIn(tween(2000)),
											resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
										),
									onClick = {
										viewModel.selectAnotherCity(city.id)
										onChangeContent(Screen.MAIN, city.id)
									},
								)
							}
						}
				}
			}
		}
	}
}

@Composable
fun CityAddingItem(
	cityName: String,
	temperature: String,
	weatherDesc: String,
	weatherIcon: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit = {},
) {
	WeatherCard(
		onClick = onClick,
		modifier = modifier,
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
	) {
		Column(
			modifier = Modifier.padding(dimensionResource(R.dimen.padding_big)),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = cityName,
					fontSize = 22.sp,
					fontWeight = FontWeight.Bold,
				)
				
				Text(
					text = temperature,
					fontSize = 22.sp,
					fontWeight = FontWeight.Bold,
					textAlign = TextAlign.Center,
				)
			}
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = weatherDesc,
					modifier = Modifier.weight(1f),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					style = MaterialTheme.typography.bodyMedium
				)
				Image(
					modifier = Modifier.requiredSize(32.dp),
					painter = painterResource(codeToResId(weatherIcon)),
					contentDescription = null,
				)
			}
		}
	}
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun CityAddingContentPreview() {
	WeatherForecastTheme {
		Surface {
			Column(
				modifier = Modifier
					.padding(dimensionResource(R.dimen.padding_medium)),
				verticalArrangement = Arrangement
					.spacedBy(dimensionResource(R.dimen.padding_medium))
			) {
				CityAddingItem(
					cityName = "Kyiv",
					temperature = "22°",
					weatherDesc = "Thunderstorm with light rain",
					weatherIcon = "11d"
				)
				CityAddingItem(
					cityName = "Poltava",
					temperature = "28°",
					weatherDesc = "Clear sky",
					weatherIcon = "01d"
				)
			}
		}
	}
}