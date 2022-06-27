package pl.training.goodweather.forecast.ports.output

import pl.training.goodweather.forecast.ports.model.DayForecast

interface ForecastProvider {

    suspend fun getForecast(city: String): List<DayForecast>

}