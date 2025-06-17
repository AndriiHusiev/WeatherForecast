package com.husiev.weather.forecast.composables.appbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
	title: String,
	modifier: Modifier = Modifier,
	navigationIcon: ImageVector? = null,
	actionIcon: ImageVector? = null,
	navigationIconContentDescription: String? = null,
	actionIconContentDescription: String? = null,
	colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
) {
	CenterAlignedTopAppBar(
		title = {
			Text(
				text = title,
				overflow = TextOverflow.Ellipsis,
				maxLines = 1,
			)
		},
		navigationIcon = {
			if (navigationIcon != null) {
				IconButton(onClick = onNavigationClick) {
					Icon(
						imageVector = navigationIcon,
						contentDescription = navigationIconContentDescription,
						tint = MaterialTheme.colorScheme.onSurface,
					)
				}
			}
		},
		actions = {
			if (actionIcon != null) {
				IconButton(onClick = onActionClick) {
					Icon(
						imageVector = actionIcon,
						contentDescription = actionIconContentDescription,
						tint = MaterialTheme.colorScheme.onSurface,
					)
				}
			}
		},
		colors = colors,
		modifier = modifier,
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview("Top App Bar")
@Composable
private fun DATopAppBarPreview() {
	WeatherForecastTheme {
		Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
			TopAppBar(
				title = "Kyiv",
			)
			TopAppBar(
				title = stringResource(R.string.select_city),
				navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
				navigationIconContentDescription = "Navigation icon",
				actionIcon = Icons.Filled.MoreVert,
				actionIconContentDescription = "Action icon",
			)
			TopAppBar(
				title = "May 12",
				navigationIconContentDescription = "Navigation icon",
				actionIcon = Icons.Filled.MoreVert,
				actionIconContentDescription = "Action icon",
			)
		}
	}
}