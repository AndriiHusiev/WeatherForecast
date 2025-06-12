package com.husiev.weather.forecast.composables.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.database.entity.codeToResId
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ForecastHoursBlock(
	forecast: List<ForecastWeatherInfo>
) {
	val lastItems = forecast.take(8)
	
	WeatherCard(modifier = Modifier.fillMaxWidth()) {
		Row(
			modifier = Modifier
				.padding(dimensionResource(R.dimen.padding_medium))
				.horizontalScroll(rememberScrollState()),
			horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_big))
		) {
			repeat(lastItems.size.coerceAtMost(8)) { i ->
				RowItemForecastBlock(lastItems[i])
			}
		}
	}
}

@Composable
fun RowItemForecastBlock(forecast: ForecastWeatherInfo) {
	Column(
		modifier = Modifier,
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			text = forecast.time,
			fontSize = 12.sp,
			fontWeight = FontWeight.Bold,
		)
		
		Image(
			modifier = Modifier.requiredSize(32.dp),
			painter = painterResource(codeToResId(forecast.weatherIcon)),
			contentDescription = null,
			contentScale = ContentScale.FillWidth,
		)
		
		Text(
			text = forecast.temperature,
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
		)
	}
}

@Preview(showBackground = true)
@Composable
fun ForecastHoursBlockPreview() {
	WeatherForecastTheme {
		Surface {
			Column(Modifier.padding(16.dp)) {
				ForecastHoursBlock(
					forecast = listOf(
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
				)
			}
		}
	}
}