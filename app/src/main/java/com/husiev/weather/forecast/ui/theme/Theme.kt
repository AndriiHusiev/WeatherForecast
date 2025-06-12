package com.husiev.weather.forecast.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val colorScheme = darkColorScheme(
	primary = primaryColor,
	surface = surfaceColor,
	onSurface = Color.White,
	background = surfaceColor,
	surfaceContainerHighest = surfaceContainerHighestColor,
	onBackground = Color.White
)

@Composable
fun WeatherForecastTheme(
	content: @Composable () -> Unit
) {
	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}