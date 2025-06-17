package com.husiev.weather.forecast.composables.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.Screen
import com.husiev.weather.forecast.database.entity.codeToResId
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@Composable
fun SelectedDayContent(
	singleDay: Int?,
	forecast: List<ForecastWeatherInfo> = emptyList(),
	onChangeContent: (Screen) -> Unit = {},
) {
	val day = forecast.filter { it.index == singleDay }
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(dimensionResource(R.dimen.padding_medium))
			.verticalScroll(rememberScrollState()),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
	) {
		repeat(day.size) { i ->
			ThreeHoursRow(day[i])
		}
	}
	
	BackHandler(
		enabled = true,
		onBack = { onChangeContent(Screen.MAIN) }
	)
}

@Composable
fun ThreeHoursRow(
	item: ForecastWeatherInfo
) {
	var expanded by rememberSaveable { mutableStateOf(false) }
	
	WeatherCard(
		modifier = Modifier
			.fillMaxWidth()
			.animateContentSize(
				animationSpec = spring(
					dampingRatio = Spring.DampingRatioMediumBouncy,
					stiffness = Spring.StiffnessLow
				)
			),
		enabled = true,
		onClick = { expanded = !expanded },
	) {
		Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
			ThreeHoursHeaderRow(
				time = item.time,
				temperature = item.temperature,
				weatherIcon = item.weatherIcon,
				pop = item.pop,
				wind = item.windSpeed,
				expanded = expanded,
			)
			
			if (expanded) {
			Spacer(Modifier.padding(dimensionResource(R.dimen.padding_extra_small)))
				
				ThreeHoursBody(
					weatherId = item.weatherId,
					feelsLike = item.feelsLike,
					windSpeed = item.windSpeed,
					humidity = item.humidity,
					pressure = item.pressure,
					cloudiness = item.cloudiness,
					precipitation = if (item.rain != NO_DATA) item.rain
					else if (item.snow != NO_DATA) item.snow
					else "0",
				)
			}
		}
	}
}

@Composable
fun ThreeHoursHeaderRow(
	time: String,
	temperature: String,
	weatherIcon: String,
	pop: String,
	wind: String,
	expanded: Boolean,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = time,
			modifier = Modifier.weight(0.6f),
			style = MaterialTheme.typography.bodyMedium
		)
		
		Text(
			text = temperature,
			modifier = Modifier.weight(0.7f),
			fontSize = 22.sp,
			fontWeight = FontWeight.Bold,
			textAlign = TextAlign.Center,
		)
		
		Image(
			modifier = Modifier.requiredSize(32.dp).weight(0.8f),
			painter = painterResource(codeToResId(weatherIcon)),
			contentDescription = null,
		)
		
		Row(
			modifier = Modifier.weight(0.8f),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.Center,
		) {
			Icon(
				painter = painterResource(R.drawable.precipitation),
				modifier = Modifier.requiredSize(16.dp),
				contentDescription = null,
			)
			Text(
				text = "$pop%",
				style = MaterialTheme.typography.bodyMedium,
				textAlign = TextAlign.Center,
			)
		}
		
		Text(
			text = wind + " " + stringResource(R.string.meter_sec),
			modifier = Modifier.weight(1.1f),
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.End,
		)
		
		Box(
			Modifier.weight(0.5f),
			contentAlignment = Alignment.CenterEnd,
		) {
			Icon(
				modifier = Modifier.requiredSize(24.dp),
				contentDescription = null,
				imageVector = if (expanded)
					Icons.Filled.Remove
				else
					Icons.Filled.Add,
			)
		}
	}
}

@Composable
fun ThreeHoursBody(
	weatherId: Int,
	feelsLike: String,
	windSpeed: String,
	humidity: String,
	pressure: String,
	cloudiness: String,
	precipitation: String,
	modifier: Modifier = Modifier,
) {
	val wId = integerArrayResource(R.array.weather_id).toList()
	val desc = stringArrayResource(R.array.weather_desc)
	val weatherDescription = desc.getOrNull(wId.indexOf(weatherId))
	
	Column(
		modifier = modifier
			.fillMaxWidth()
			.border(
				width = 1.dp,
				color = Color.White,
				shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium))
			)
			.padding(dimensionResource(R.dimen.padding_medium)),
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
	) {
		weatherDescription?.let {
			Text(
				text = weatherDescription,
				modifier = Modifier.fillMaxWidth(),
				textAlign = TextAlign.Center,
				fontSize = 20.sp,
				fontWeight = FontWeight.Bold,
			)
		}
		
		Row(
			modifier = Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			ThreeHoursBodyItem(
				title = stringResource(R.string.feels_like),
				value = feelsLike,
				painter = painterResource(R.drawable.feelslike),
				modifier = Modifier.weight(1f)
			)
			ThreeHoursBodyItem(
				title = stringResource(R.string.wind),
				value = windSpeed + " " + stringResource(R.string.meter_sec),
				painter = painterResource(R.drawable.wind),
				modifier = Modifier.weight(1f)
			)
		}
		
		Row(
			modifier = modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			ThreeHoursBodyItem(
				title = stringResource(R.string.humidity),
				value = "$humidity%",
				painter = painterResource(R.drawable.humidity),
				modifier = Modifier.weight(1f)
			)
			ThreeHoursBodyItem(
				title = stringResource(R.string.pressure),
				value = pressure + " " + stringResource(R.string.hpa),
				painter = painterResource(R.drawable.pressure),
				modifier = Modifier.weight(1f)
			)
		}
		
		Row(
			modifier = modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
		) {
			ThreeHoursBodyItem(
				title = stringResource(R.string.cloudiness),
				value = "$cloudiness%",
				painter = painterResource(R.drawable.cloud),
				modifier = Modifier.weight(1f)
			)
			ThreeHoursBodyItem(
				title = stringResource(R.string.precipitation),
				value = precipitation + " " + stringResource(R.string.mm),
				painter = painterResource(R.drawable.precipitation),
				modifier = Modifier.weight(1f)
			)
		}
	}
}

@Composable
fun ThreeHoursBodyItem(
	title: String,
	value: String,
	painter: Painter,
	modifier: Modifier = Modifier,
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
	) {
		Icon(
			painter = painter,
			modifier = Modifier.requiredSize(24.dp),
			contentDescription = null,
		)
		
		Column(
			modifier = Modifier,
		) {
			Text(
				text = title,
				modifier = Modifier,
				fontSize = 12.sp,
			)
			
			Text(
				text = value,
				modifier = Modifier,
				fontSize = 18.sp,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center,
			)
		}
	}
}

@Preview(showBackground = true)
@Preview(showBackground = true, heightDp = 640, widthDp = 360)
@Preview(showBackground = true, heightDp = 1000, widthDp = 448)
@Composable
fun SelectedDayContentPreview() {
	WeatherForecastTheme {
		Surface {
			Column {
				SelectedDayContent(
					-1,
					listOf(
						ForecastWeatherInfo(
							date = "12.06",
							time = "18:00",
							temperature = "22°",
							weatherIcon = "03d",
							pop = "15",
							windSpeed = "4",
							feelsLike = "21°",
							humidity = "50",
							pressure = "1014",
							cloudiness = "87",
							rain = NO_DATA,
							snow = NO_DATA,
							weatherId = 804,
						)
					)
				)
				SelectedDayContent(
					-1,
					listOf(
						ForecastWeatherInfo(
							date = "12.06",
							time = "21:00",
							temperature = "18°",
							weatherIcon = "09d",
							pop = "92",
							windSpeed = "10",
							feelsLike = "15°",
							humidity = "98",
							pressure = "990",
							cloudiness = "100",
							rain = "1.8",
							snow = NO_DATA,
							weatherId = 500,
						)
					)
				)
				SelectedDayContent(
					-1,
					listOf(
						ForecastWeatherInfo(
							date = "13.06",
							time = "00:00",
							temperature = "9°",
							weatherIcon = "11d",
							pop = "100",
							windSpeed = "15",
							feelsLike = "15°",
							humidity = "100",
							pressure = "980",
							cloudiness = "100",
							rain = "3.2",
							snow = NO_DATA,
							weatherId = 200,
						)
					)
				)
			}
		}
	}
}
