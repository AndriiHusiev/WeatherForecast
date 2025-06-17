package com.husiev.weather.forecast.composables.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.database.entity.codeToResId
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ForecastDaysBlock(
	forecast: List<ForecastBriefInfo>,
	onClick: (Int) -> Unit = {},
) {
	WeatherCard(modifier = Modifier.fillMaxWidth()) {
		Column(
			modifier = Modifier
				.padding(dimensionResource(R.dimen.padding_medium))
				.fillMaxWidth(),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
		) {
			repeat(forecast.size) { i ->
				SingleDayHeaderRow(
					date = forecast[i].date,
					dayOfWeek = forecast[i].dayOfWeek,
					weatherIcon = forecast[i].weatherIcon,
					temperatureRange = forecast[i].temperatureRange,
					onClick = { onClick(i) },
				)
			}
		}
	}
}

@Composable
fun SingleDayHeaderRow(
	date: String,
	dayOfWeek: String,
	weatherIcon: String,
	temperatureRange: String,
	modifier: Modifier = Modifier,
	onClick: () -> Unit = {},
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onClick() },
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		Text(
			text = date,
			modifier = Modifier.weight(0.7f),
			style = MaterialTheme.typography.bodyMedium
		)
		Text(
			text = dayOfWeek,
			modifier = Modifier.weight(1.5f),
			style = MaterialTheme.typography.bodyMedium
		)
		Image(
			modifier = Modifier.requiredSize(32.dp).weight(0.5f),
			painter = painterResource(codeToResId(weatherIcon)),
			contentDescription = null,
		)
		Text(
			text = temperatureRange,
			modifier = Modifier.weight(0.8f),
			style = MaterialTheme.typography.bodyMedium
		)
		Icon(
			modifier = Modifier.requiredSize(24.dp).weight(0.4f),
			imageVector = Icons.Filled.ChevronRight,
			contentDescription = null,
			tint = Color.White,
		)
	}
}

@Preview(showBackground = true)
@Preview(showBackground = true, heightDp = 640, widthDp = 360)
@Preview(showBackground = true, heightDp = 1000, widthDp = 448)
@Composable
fun ForecastDaysBlockPreview() {
	WeatherForecastTheme {
		Surface {
			Column(Modifier.padding(16.dp)) {
				ForecastDaysBlock(
					forecast = listOf(
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
					)
				)
			}
		}
	}
}