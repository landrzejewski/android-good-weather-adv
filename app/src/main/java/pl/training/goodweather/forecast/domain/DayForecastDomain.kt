package pl.training.goodweather.forecast.domain

import java.util.*

class DayForecastDomain(
    val icon: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val data: Date
)