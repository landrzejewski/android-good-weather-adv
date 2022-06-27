package pl.training.goodweather.forecast.ports.model

import java.util.*

class DayForecast(
    val icon: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val data: Date
)