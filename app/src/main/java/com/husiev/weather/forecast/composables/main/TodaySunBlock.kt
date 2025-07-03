package com.husiev.weather.forecast.composables.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodaySunBlock(
	curWeather: CurrentWeatherInfo
) {
	val sliderPosition = remember { Animatable(curWeather.sunrise) }
	LaunchedEffect(key1 = curWeather, block = {
		sliderPosition.animateTo(Calendar.getInstance().time.time.toFloat(), tween(3000))
	})
	
	WeatherCard(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.padding(
					start =  dimensionResource(R.dimen.padding_medium),
					end =  dimensionResource(R.dimen.padding_medium),
					top =  dimensionResource(R.dimen.padding_medium))
				.fillMaxWidth(),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Icon(
						painter = painterResource(R.drawable.sunrise),
						contentDescription = null,
						modifier = Modifier.size(48.dp)
					)
					Text(
						text = stringResource(R.string.sunrise),
						style = MaterialTheme.typography.labelMedium,
					)
				}
				Column(horizontalAlignment = Alignment.CenterHorizontally) {
					Icon(
						painter = painterResource(R.drawable.sunset),
						contentDescription = null,
						modifier = Modifier.size(48.dp)
					)
					Text(
						text = stringResource(R.string.sunset),
						style = MaterialTheme.typography.labelMedium,
					)
				}
			}
		}
		
		Slider(
			value = sliderPosition.value,
			onValueChange = { },
			valueRange = curWeather.sunrise..curWeather.sunset,
			enabled = false,
			thumb = {
				Icon(
					imageVector = Icons.Filled.WbSunny,
					contentDescription = null,
					modifier = Modifier.size(32.dp)
				)
			}
		)
		
		Column(
			modifier = Modifier
				.padding(
					start =  dimensionResource(R.dimen.padding_medium),
					end =  dimensionResource(R.dimen.padding_medium),
					bottom =  dimensionResource(R.dimen.padding_medium))
				.fillMaxWidth(),
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = curWeather.sunriseTime,
					fontSize = 24.sp,
					fontWeight = FontWeight.Bold,
				)
				Text(
					text = curWeather.sunsetTime,
					fontSize = 24.sp,
					fontWeight = FontWeight.Bold,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TodaySunBlockPreview() {
	WeatherForecastTheme {
		Surface {
			Column(Modifier.padding(16.dp)) {
				TodaySunBlock(
					CurrentWeatherInfo(
						sunrise = 1749260917000f,
						sunset = 1749318859000f,
						sunriseTime = "04:05",
						sunsetTime = "20:46",
					)
				)
				TodaySunBlock(
					CurrentWeatherInfo(
						sunrise = 1749329164000f,
						sunset = 1749382865000f,
						sunriseTime = "04:05",
						sunsetTime = "20:46",
					)
				)
			}
		}
	}
}