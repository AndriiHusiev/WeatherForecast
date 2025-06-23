package com.husiev.weather.forecast.composables.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import com.husiev.weather.forecast.R

private const val ALPHA = 0.2f

@Composable
fun WeatherCard(
	modifier: Modifier = Modifier,
	shape: Shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
	colors: CardColors = CardDefaults.cardColors(
		containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(ALPHA)),
	elevation: CardElevation = CardDefaults.cardElevation(),
	border: BorderStroke? = null,
	content: @Composable (ColumnScope.() -> Unit)
) {
	Card(
		modifier = modifier,
		shape = shape,
		colors = colors,
		elevation = elevation,
		border = border,
		content = content
	)
}

@Composable
fun WeatherCard(
	onClick: () -> Unit,
	modifier: Modifier = Modifier,
	shape: Shape = RoundedCornerShape(dimensionResource(R.dimen.padding_medium)),
	colors: CardColors = CardDefaults.cardColors(
		containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(ALPHA)),
	elevation: CardElevation = CardDefaults.cardElevation(),
	border: BorderStroke? = null,
	content: @Composable (ColumnScope.() -> Unit)
) {
	Card(
		onClick = onClick,
		modifier = modifier,
		shape = shape,
		colors = colors,
		elevation = elevation,
		border = border,
		content = content
	)
}