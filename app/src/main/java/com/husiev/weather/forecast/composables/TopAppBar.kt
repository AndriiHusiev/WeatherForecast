package com.husiev.weather.forecast.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
	screen: Screen,
	modifier: Modifier = Modifier,
	name: String = "",
	actionIcon: ImageVector? = null,
	navigationIconContentDescription: String? = stringResource(R.string.back),
	actionIconContentDescription: String? = null,
	colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
	onNavigationClick: () -> Unit = {},
	onActionClick: () -> Unit = {},
) {
	var header = if (name.isEmpty()) stringResource(R.string.app_name) else name
	var navigationIcon: ImageVector? = Icons.AutoMirrored.Filled.ArrowBack
	when(screen) {
		Screen.MAIN -> navigationIcon = Icons.Filled.Search
		Screen.SEL_CITY -> header = stringResource(R.string.select_city)
	}
	
	CenterAlignedTopAppBar(
		title = {
			Text(
				text = header,
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
		Column {
			TopAppBar(
				name = "Kyiv",
				screen = Screen.MAIN,
			)
			Spacer(Modifier.padding(vertical = dimensionResource(R.dimen.padding_small)))
			TopAppBar(
				navigationIconContentDescription = "Navigation icon",
				actionIcon = Icons.Filled.MoreVert,
				actionIconContentDescription = "Action icon",
				screen = Screen.SEL_CITY,
			)
		}
	}
}