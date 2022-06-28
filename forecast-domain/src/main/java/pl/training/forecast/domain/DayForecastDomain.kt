package pl.training.forecast.domain

import java.util.*

internal class DayForecastDomain(
    val icon: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val date: Date
)