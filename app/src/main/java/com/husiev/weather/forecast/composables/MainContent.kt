package com.husiev.weather.forecast.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.husiev.weather.forecast.database.entity.CityInfo
import com.husiev.weather.forecast.ui.theme.WeatherForecastTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
	cityInfo: CityInfo?,
	onChangeContent: (Screen) -> Unit = {},
) {
	if (cityInfo == null)
		EmptyMainContent(onChangeContent)
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
			MainContent(null)
		}
	}
}