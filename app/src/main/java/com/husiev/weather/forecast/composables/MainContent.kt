package com.husiev.weather.forecast.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.main.CityInfo
import com.husiev.weather.forecast.composables.main.CurrentWeatherInfo
import com.husiev.weather.forecast.composables.main.NO_DATA
import com.husiev.weather.forecast.composables.main.TodayHeaderBlock
import com.husiev.weather.forecast.composables.main.TodaySecondaryBlock
import com.husiev.weather.forecast.composables.main.TodaySunBlock
import com.husiev.weather.forecast.composables.main.WeatherInfo
import com.husiev.weather.forecast.database.entity.degToDir
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
	weather: WeatherInfo,
	onChangeContent: (Screen) -> Unit = {},
) {
	if (weather.cityInfo == null)
		EmptyMainContent(onChangeContent)
	else {
		Column(modifier = Modifier
			.fillMaxWidth()
			.padding(dimensionResource(R.dimen.padding_medium))
			.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
		) {
			TodayHeaderBlock(curWeather = weather.current)
			TodaySecondaryBlock(curWeather = weather.current)
			TodaySunBlock(curWeather = weather.current)
		}
	}
}

@Composable
fun EmptyMainContent(
	onChangeContent: (Screen) -> Unit = {}
) {
	Text(
		text = stringResource(R.string.select_a_city),
		style = MaterialTheme.typography.bodyLarge,
		textAlign = TextAlign.Center,
		modifier = Modifier.padding(
			horizontal = dimensionResource(R.dimen.padding_extra_large),
			vertical = dimensionResource(R.dimen.padding_large)
		),
	)
	Button(
		modifier = Modifier
			.padding(
				horizontal = dimensionResource(R.dimen.padding_extra_large),
				vertical = dimensionResource(R.dimen.padding_big)
			)
			.fillMaxWidth(),
		shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)),
		onClick = { onChangeContent(Screen.SEL_CITY) }
	) {
		Text(
			text = stringResource(R.string.select_city),
			modifier = Modifier.padding(dimensionResource(R.dimen.padding_extra_small)),
			style = MaterialTheme.typography.titleLarge,
		)
	}
}

@Preview(showBackground = true)
@Preview(
	showBackground = true,
	uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun StartContentPreview() {
	WeatherForecastTheme {
		Column {
			MainContent(WeatherInfo(
				CityInfo(0, 0f, 0f, "UA", null, "Kyiv"),
				CurrentWeatherInfo(
					temperature = "22",
					feelsLike = "15",
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
					sunrise = 1748835252,
					sunset = 1748891218,
				)
			))
		}
	}
}