package com.husiev.weather.forecast.composables.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.database.entity.degToDir
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@Composable
fun TodaySecondaryBlock(
	curWeather: CurrentWeatherInfo
) {
	Column(
		modifier = Modifier.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
	) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
		) {
			SingleTodayCard(
				header = stringResource(R.string.pressure),
				descBig = curWeather.pressure,
				descSmall = stringResource(R.string.hpa),
				icon = Icons.Filled.FileDownload,
				modifier = Modifier.weight(1f)
			)
			SingleTodayCard(
				header = stringResource(R.string.humidity),
				descBig = curWeather.humidity,
				descSmall = "%",
				icon = Icons.Filled.WaterDrop,
				modifier = Modifier.weight(1f)
			)
			SingleTodayCard(
				header = stringResource(R.string.visibility),
				descBig = curWeather.visibility,
				descSmall = stringResource(R.string.km),
				icon = Icons.Filled.RemoveRedEye,
				modifier = Modifier.weight(1f)
			)
		}
		
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
		) {
			SingleTodayCard(
				header = stringResource(R.string.wind),
				descBig = curWeather.windSpeed,
				descSmall = stringResource(R.string.meter_sec),
				icon = Icons.Filled.Air,
				modifier = Modifier.weight(1f)
			)
			SingleTodayCard(
				header = stringResource(R.string.wind_gust),
				descBig = curWeather.windGust,
				descSmall = stringResource(R.string.meter_sec),
				icon = Icons.Filled.Air,
				modifier = Modifier.weight(1f)
			)
			SingleTodayCard(
				header = stringResource(R.string.wind_dir),
				descBig = curWeather.windDir,
				descSmall = "",
				icon = Icons.Filled.ArrowUpward,
				modifier = Modifier.weight(1f),
				angle = curWeather.windDeg
			)
		}
	}
}

@Composable
fun SingleTodayCard(
	header: String,
	descBig: String,
	descSmall: String,
	icon: ImageVector,
	modifier: Modifier = Modifier,
	angle: Float? = null
) {
	ElevatedCard(
		modifier = modifier.aspectRatio(1f),
		shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
		elevation = CardDefaults.elevatedCardElevation(
			dimensionResource(R.dimen.padding_extra_small)
		)
	) {
		Column(
			modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)).fillMaxHeight(),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = header,
				style = MaterialTheme.typography.labelMedium,
			)
			Icon(
				imageVector = icon,
				contentDescription = null,
				modifier = Modifier.graphicsLayer {
					angle?.let { rotationZ = angle }
				}
			)
			Row(
				verticalAlignment = Alignment.Bottom
			) {
				Text(
					text = descBig,
					modifier = Modifier.alignByBaseline(),
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold,
				)
				Text(
					text = descSmall,
					modifier = Modifier.alignByBaseline(),
					fontSize = 10.sp,
					fontWeight = FontWeight.Bold,
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TodaySecondaryBlockPreview() {
	WeatherForecastTheme {
		Column {
			TodaySecondaryBlock(CurrentWeatherInfo(
				pressure = "1018",
				humidity = "44",
				visibility = "10",
				windSpeed = "13",
				windGust = "20",
				windDeg = 290f,
				windDir = 290.degToDir(),
			))
		}
	}
}