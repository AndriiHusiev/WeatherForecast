package com.husiev.weather.forecast.composables.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.database.entity.codeToResId
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@Composable
fun TodayHeaderBlock(
	curWeather: CurrentWeatherInfo
) {
	val wId = integerArrayResource(R.array.weather_id).toList()
	val desc = stringArrayResource(R.array.weather_desc)
	val weatherDescription = desc.getOrElse(wId.indexOf(curWeather.weatherId)) { NO_DATA } +
			if (curWeather.rain != NO_DATA) curWeather.rain + stringResource(R.string.mm_h)
			else if (curWeather.snow != NO_DATA) curWeather.snow + stringResource(R.string.mm_h)
			else if (curWeather.cloudiness != NO_DATA) curWeather.cloudiness
			else ""
	
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = dimensionResource(R.dimen.padding_medium)),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = curWeather.temperature,
			fontSize = 72.sp,
			fontWeight = FontWeight.Bold,
		)
		Text(
			text = stringResource(R.string.feels_like) + " ${curWeather.feelsLike}",
			style = MaterialTheme.typography.labelLarge,
		)
		Row(verticalAlignment = Alignment.CenterVertically) {
			Text(
				text = weatherDescription,
				style = MaterialTheme.typography.labelLarge,
			)
			Image(
				modifier = Modifier.requiredSize(32.dp),
				painter = painterResource(codeToResId(curWeather.weatherIcon)),
				contentDescription = null,
				contentScale = ContentScale.FillWidth,
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun ShortInfoBlockPreview() {
	WeatherForecastTheme {
		Column {
			TodayHeaderBlock(CurrentWeatherInfo(
				temperature = "22",
				feelsLike = "15",
				weatherId = 202,
				weatherIcon = "11d",
				rain = ", 22",
				snow = NO_DATA,
				cloudiness = NO_DATA
			))
		}
	}
}