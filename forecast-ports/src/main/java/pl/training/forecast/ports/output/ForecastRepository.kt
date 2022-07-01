package pl.training.forecast.ports.output

import pl.training.forecast.ports.model.DayForecast

interface ForecastRepository {

    suspend fun save(city: String, forecast: List<DayForecast>)

    suspend fun load(city: String): List<DayForecast>

    suspend fun clear()

}