package com.husiev.weather.forecast.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.husiev.weather.forecast.composables.main.CurrentWeatherInfo
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.main.NO_DATA
import kotlin.math.roundToInt

@Entity(
	tableName = "current_weather",
	foreignKeys = [ForeignKey(
		entity = CityEntity::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("city_id"),
		onDelete = ForeignKey.CASCADE,
	)],
	indices = [Index(value = ["city_id"], unique = true)]
)
data class CurrentWeatherEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	@ColumnInfo(name = "city_id")
	val cityId: Int,
	val visibility: Int,
	@ColumnInfo(name = "weather_id")
	val weatherId: Int,
	@ColumnInfo(name = "weather_group")
	val weatherGroup: String,
	@ColumnInfo(name = "weather_desc")
	val weatherDescription: String,
	@ColumnInfo(name = "weather_icon")
	val weatherIcon: String,
	val temperature: Float,
	@ColumnInfo(name = "feels_like")
	val feelsLike: Float,
	val pressure: Int,
	val humidity: Int,
	val sunrise: Int,
	val sunset: Int,
	@ColumnInfo(name = "wind_speed")
	val windSpeed: Float? = null,
	@ColumnInfo(name = "wind_deg")
	val windDeg: Int? = null,
	@ColumnInfo(name = "wind_gust")
	val windGust: Float? = null,
	val cloudiness: Int? = null,
	val rain: Float? = null,
	val snow: Float? = null,
)

fun List<CurrentWeatherEntity>.asExternalModel(): CurrentWeatherInfo {
	if (this.isEmpty()) return CurrentWeatherInfo()
	
	return CurrentWeatherInfo(
		temperature = this[0].temperature.roundToInt().toString() + "°",
		feelsLike = this[0].feelsLike.roundToInt().toString() + "°",
		weatherId = this[0].weatherId,
		weatherIcon = this[0].weatherIcon,
		pressure = this[0].pressure.toString(),
		humidity = this[0].humidity.toString(),
		visibility = if (this[0].visibility < 1000)
			(this[0].visibility / 1000f).toString()
		else
			(this[0].visibility / 1000).toString(),
		sunrise = this[0].sunrise,
		sunset = this[0].sunset,
		windSpeed = this[0].windSpeed?.roundToInt()?.toString() ?: "",
		windDeg = this[0].windDeg?.toFloat(),
		windDir = this[0].windDeg.degToDir(),
		windGust = this[0].windGust?.roundToInt()?.toString() ?: NO_DATA,
		cloudiness = this[0].cloudiness?.let { ", $it%" } ?: NO_DATA,
		rain = this[0].rain?.let { ", $it" } ?: NO_DATA,
		snow = this[0].snow?.let { ", $it" } ?: NO_DATA
	)
}

fun codeToResId(code: String) = when(code) {
	"01d" -> R.drawable.icon01d
	"01n" -> R.drawable.icon01n
	"02d" -> R.drawable.icon02d
	"02n" -> R.drawable.icon02n
	"03d" -> R.drawable.icon03d
	"03n" -> R.drawable.icon03n
	"04d" -> R.drawable.icon04d
	"04n" -> R.drawable.icon04n
	"09d" -> R.drawable.icon09d
	"09n" -> R.drawable.icon09n
	"10d" -> R.drawable.icon10d
	"10n" -> R.drawable.icon10n
	"11d" -> R.drawable.icon11d
	"11n" -> R.drawable.icon11n
	"13d" -> R.drawable.icon13d
	"13n" -> R.drawable.icon13n
	"50d" -> R.drawable.icon50d
	"50n" -> R.drawable.icon50n
	else -> R.drawable.icon02d
}

fun Int?.degToDir(): String {
	if (this != null) {
		val compassPoints = arrayOf("N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW")
		val degreesPerPoint = 360.0 / 16.0
		val shiftedDegrees = this + (degreesPerPoint / 2.0)
		val index = (shiftedDegrees / degreesPerPoint).toInt() % 16
		return compassPoints[index]
	} else
		return NO_DATA
}