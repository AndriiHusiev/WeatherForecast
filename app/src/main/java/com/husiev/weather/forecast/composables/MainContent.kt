package com.husiev.weather.forecast.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.appbar.TopAppBar
import com.husiev.weather.forecast.composables.main.*
import com.husiev.weather.forecast.database.entity.degToDir
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
	selectedId: Int,
	weather: WeatherInfo,
	sharedTransitionScope: SharedTransitionScope,
	animatedContentScope: AnimatedContentScope,
	onChangeContent: (Screen, Int) -> Unit,
	onActionClick: (Screen) -> Unit = {},
) {
	with(sharedTransitionScope) {
		Scaffold(
			modifier = Modifier.fillMaxSize()
				.sharedBounds(
					sharedContentState = rememberSharedContentState(
						key = selectedId,
					),
					animatedVisibilityScope = animatedContentScope,
					exit = fadeOut(tween(500)),
					enter = fadeIn(tween(3000)),
					resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds
				),
			topBar = {
				TopAppBar(
					title = weather.cityInfo?.name ?: stringResource(R.string.app_name),
					navigationIcon = Icons.Filled.Search,
					actionIcon = Icons.AutoMirrored.Filled.FormatListBulleted,
					onNavigationClick = { onChangeContent(Screen.SEL_CITY, 0) },
					onActionClick = { onActionClick(Screen.ADD_CITY) },
				)
			}
		) { innerPadding ->
			if (weather.cityInfo == null)
				EmptyMainContent(Modifier.padding(innerPadding))
			else {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(innerPadding)
						.padding(horizontal = dimensionResource(R.dimen.padding_medium))
						.verticalScroll(rememberScrollState()),
					verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
				) {
					TodayHeaderBlock(curWeather = weather.current)
					TodaySecondaryBlock(curWeather = weather.current)
					ForecastHoursBlock(forecast = weather.fullForecast)
					ForecastDaysBlock(
						forecast = weather.briefForecast,
						onClick = { onChangeContent(Screen.SEL_DAY, it) })
					TodaySunBlock(curWeather = weather.current)
				}
			}
		}
	}
}

@Composable
fun EmptyMainContent(
	modifier: Modifier = Modifier,
	fontColor: Color = Color.Unspecified,
) {
	Text(
		text = stringResource(R.string.select_a_city),
		color = fontColor,
		style = MaterialTheme.typography.bodyLarge,
		textAlign = TextAlign.Center,
		modifier = modifier.fillMaxWidth().padding(
			horizontal = dimensionResource(R.dimen.padding_extra_large),
			vertical = dimensionResource(R.dimen.padding_large)
		),
	)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun StartContentPreview() {
	WeatherForecastTheme {
		SharedTransitionLayout {
			Surface {
				Column {
					EmptyMainContent()
					MainContent(
						0,
						WeatherInfo(
							cityInfo = CityInfo(0, 0f, 0f, "UA", null, "Kyiv"),
							current = CurrentWeatherInfo(
								temperature = "22°",
								feelsLike = "15°",
								weatherId = 202,
								weatherIcon = "11d",
								rain = ", 22",
								snow = NO_DATA,
								cloudiness = NO_DATA,
								pressure = "1018",
								humidity = "44",
								visibility = "10",
								windSpeed = "13",
								windGust = "20",
								windDeg = 290f,
								windDir = 290.degToDir(),
								sunrise = 1748835252000f,
								sunset = 1748891218000f,
								sunriseTime = "04:05",
								sunsetTime = "20:46",
							),
							briefForecast = listOf(
								ForecastBriefInfo(
									date = SimpleDateFormat(
										"dd.MM",
										Locale.getDefault()
									).format(1749119200000L),
									dayOfWeek = "Today",
									weatherIcon = "01d",
									temperatureRange = "18° / 30°"
								),
								ForecastBriefInfo(
									date = SimpleDateFormat(
										"dd.MM",
										Locale.getDefault()
									).format(1749219200000L),
									dayOfWeek = "Tomorrow",
									weatherIcon = "02d",
									temperatureRange = "16° / 25°"
								),
								ForecastBriefInfo(
									date = SimpleDateFormat(
										"dd.MM",
										Locale.getDefault()
									).format(1749319200000L),
									dayOfWeek = "Mon",
									weatherIcon = "03d",
									temperatureRange = "15° / 22°"
								),
								ForecastBriefInfo(
									date = SimpleDateFormat(
										"dd.MM",
										Locale.getDefault()
									).format(1749405600000L),
									dayOfWeek = "Tue",
									weatherIcon = "04d",
									temperatureRange = "14° / 20°"
								),
								ForecastBriefInfo(
									date = SimpleDateFormat(
										"dd.MM",
										Locale.getDefault()
									).format(1749500600000L),
									dayOfWeek = "Wed",
									weatherIcon = "09d",
									temperatureRange = "12° / 18°"
								),
							),
							fullForecast = listOf(
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749319200000L),
									temperature = "22°",
									weatherIcon = "01n",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749330000000L),
									temperature = "21°",
									weatherIcon = "02n",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749340800000L),
									temperature = "19°",
									weatherIcon = "01n",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749351600000L),
									temperature = "15°",
									weatherIcon = "01d",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749362400000L),
									temperature = "22°",
									weatherIcon = "02d",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749373200000L),
									temperature = "26°",
									weatherIcon = "03d",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749384000000L),
									temperature = "28°",
									weatherIcon = "10d",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749394800000L),
									temperature = "27°",
									weatherIcon = "04d",
								),
								ForecastWeatherInfo(
									time = SimpleDateFormat(
										"HH:mm",
										Locale.getDefault()
									).format(1749405600000L),
									temperature = "21°",
									weatherIcon = "01d",
								),
							)
						),
						onChangeContent = { a, b -> },
						sharedTransitionScope = this@SharedTransitionLayout,
						animatedContentScope = this as AnimatedContentScope,
					)
				}
			}
		}
	}
}