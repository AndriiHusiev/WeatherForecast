package com.husiev.weather.forecast.database.entity

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.husiev.weather.forecast.R
import com.husiev.weather.forecast.composables.main.ForecastBriefInfo
import com.husiev.weather.forecast.composables.main.ForecastWeatherInfo
import com.husiev.weather.forecast.composables.main.NO_DATA
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.collections.forEach
import kotlin.math.roundToInt

@Entity(
	tableName = "forecast_weather",
	foreignKeys = [ForeignKey(
		entity = CityEntity::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("city_id"),
		onDelete = ForeignKey.CASCADE,
	)],
	indices = [Index(value = ["city_id", "datetime"], unique = true)]
)
data class ForecastWeatherEntity(
	@PrimaryKey(autoGenerate = true)
	val id: Int = 0,
	@ColumnInfo(name = "city_id")
	val cityId: Int,
	val datetime: Int,
	val temperature: Float,
	@ColumnInfo(name = "feels_like")
	val feelsLike: Float,
	val pressure: Int,
	val humidity: Int,
	val visibility: Int?,
	val pop: Float,
	@ColumnInfo(name = "weather_id")
	val weatherId: Int,
	@ColumnInfo(name = "weather_group")
	val weatherGroup: String,
	@ColumnInfo(name = "weather_desc")
	val weatherDescription: String,
	@ColumnInfo(name = "weather_icon")
	val weatherIcon: String,
	@ColumnInfo(name = "wind_speed")
	val windSpeed: Float? = null,
	@ColumnInfo(name = "wind_deg")
	val windDeg: Int? = null,
	@ColumnInfo(name = "wind_gust")
	val windGust: Float? = null,
	val cloudiness: Int? = null,
	val rain: Float? = null,
	val snow: Float? = null,
	val pod: String,
)

private const val LIST_SIZE = 40

fun List<ForecastWeatherEntity>.asBriefModel(context: Context): List<ForecastBriefInfo> {
	val list = mutableListOf<ForecastBriefInfo>()
	val subList = this.takeLast(LIST_SIZE)
	
	repeat(6) { i ->
		// Create a sublist in which the elements belong to the same day
		val dayList = subList.filter { getDayDifference(it.datetime) == i }
		if (dayList.isEmpty()) return@repeat
		
		// Let's analyze this sublist
		val dayOfWeek = when(i) {
			0 -> context.resources.getString(R.string.today)
			1 -> context.resources.getString(R.string.tomorrow)
			else -> SimpleDateFormat("EEE", Locale.getDefault()).format(dayList[0].datetime * 1000L)
		}
		val weatherIcon = findMostFrequentWeather(dayList.map { it.weatherIcon })
		val maxTemp = dayList.maxOf { it.temperature }.roundToInt()
		val minTemp = dayList.minOf { it.temperature }.roundToInt()
		
		list.add(ForecastBriefInfo(
			index = i,
			date = SimpleDateFormat("dd.MM", Locale.getDefault()).format(dayList[0].datetime * 1000L),
			dayOfWeek = dayOfWeek,
			weatherIcon = weatherIcon,
			temperatureRange = "$minTemp° / $maxTemp°"
		))
	}
	
	return list
}

fun List<ForecastWeatherEntity>.asFullModel(): List<ForecastWeatherInfo> {
	val list = mutableListOf<ForecastWeatherInfo>()
	val subList = this.takeLast(LIST_SIZE)
	val dayIndex = getDaysDifference(subList)
	
	subList.forEachIndexed { i, item ->
		list.add(ForecastWeatherInfo(
			index = dayIndex[i],
			date = SimpleDateFormat(
				"dd.MM",
				Locale.getDefault()).format(item.datetime * 1000L),
			dateWeekDay = SimpleDateFormat(
				"EEE, d MMMM",
				Locale.getDefault()).format(item.datetime * 1000L),
			time = SimpleDateFormat(
				"HH:mm",
				Locale.getDefault()).format(item.datetime * 1000L),
			temperature = item.temperature.roundToInt().toString() + "°",
			feelsLike = item.feelsLike.roundToInt().toString() + "°",
			weatherId = item.weatherId,
			weatherIcon = item.weatherIcon,
			pressure = item.pressure.toString(),
			humidity = item.humidity.toString(),
			visibility = item.visibility?.let { if (it < 1000)
				(it / 1000f).toString()
			else
				(it / 1000).toString()
			} ?: NO_DATA,
			windSpeed = if (item.windSpeed == null) "" else item.windSpeed.roundToInt().toString(),
			windDeg = item.windDeg?.toFloat(),
			windDir = item.windDeg.degToDir(),
			windGust = item.windGust?.toString() ?: NO_DATA,
			cloudiness = item.cloudiness?.let { "$it" } ?: "0",
			rain = item.rain?.let { "$it" } ?: NO_DATA,
			snow = item.snow?.let { "$it" } ?: NO_DATA,
			pop = String.format(Locale.getDefault(), "%d", (item.pop * 100).roundToInt()),
		))
	}
	
	return list
}

fun getDaysDifference(forecast: List<ForecastWeatherEntity>): List<Int> {
	val list = mutableListOf<Int>()
	
	forecast.forEach {
		list.add(getDayDifference(it.datetime))
	}
	
	return list
}

fun getDayDifference(datetime: Int): Int {
	val today = LocalDate.now(ZoneId.systemDefault())
	val targetDate = Instant.ofEpochMilli(datetime * 1000L)
		.atZone(ZoneId.systemDefault())
		.toLocalDate()
	
	return ChronoUnit.DAYS.between(today, targetDate).toInt()
}

fun findMostFrequentWeather(list: List<String>): String {
	val itemCounts: Map<String, Int> = list.groupingBy { it }.eachCount()
	val maxFrequency = itemCounts.values.max()
	val mostFrequentItems: List<String> = itemCounts
		.filterValues { it == maxFrequency }
		.keys // Get the items (keys) that have the max frequency
		.toList()
	
	if (mostFrequentItems.size == 1) {
		return mostFrequentItems.first()
	}
	
	return mostFrequentItems.maxBy { item ->
		// Regular expression to find digit in the string
		val regex = "\\d+".toRegex()
		val matchResult = regex.find(item)
		
		// Extract the number, parse it as Int
		matchResult!!.value.toInt()
	}
}