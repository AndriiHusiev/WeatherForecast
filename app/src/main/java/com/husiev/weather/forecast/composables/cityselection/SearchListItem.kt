package com.husiev.weather.forecast.composables.cityselection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.network.NetworkCityInfo
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@Composable
fun SearchListItem(
	cityInfo: NetworkCityInfo,
	modifier: Modifier = Modifier,
	onClick: (NetworkCityInfo) -> Unit = {}
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickable(onClick = { onClick(cityInfo) }),
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = cityInfo.name + ", " + cityInfo.country +
						(cityInfo.state?.let { ", $it" } ?: ""),
				modifier = Modifier
					.padding(
						horizontal = dimensionResource(R.dimen.padding_large),
						vertical = dimensionResource(R.dimen.padding_small)
					),
				overflow = TextOverflow.Ellipsis,
				maxLines = 1,
			)
		}
		HorizontalDivider(modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)))
	}
}

@Preview(showBackground = true)
@Composable
fun SearchListItemPreview() {
	WeatherForecastTheme {
		SearchListItem(
			cityInfo = NetworkCityInfo(
				name = "Kyiv",
				lat = 50.4500336f,
				lon = 30.5241361f,
				country = "UA",
				state = "Mykolaiv Oblast",
				localNames = mapOf(
					"ru" to "Киев",
					"en" to "Kyiv",
					"uk" to "Київ",
					"de" to "Kyjiw",
				)
			)
		)
	}
}