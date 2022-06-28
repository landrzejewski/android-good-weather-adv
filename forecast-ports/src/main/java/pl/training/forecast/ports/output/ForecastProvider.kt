package pl.training.forecast.ports.output

import pl.training.forecast.ports.model.DayForecast

interface ForecastProvider {

    suspend fun getForecast(city: String): List<DayForecast>

}