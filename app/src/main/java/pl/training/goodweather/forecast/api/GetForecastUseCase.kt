package pl.training.goodweather.forecast.api

import pl.training.goodweather.forecast.model.DayForecast

interface GetForecastUseCase {

    suspend fun loadForecast(city: String): List<DayForecast>

    suspend fun getCachedForecast(city: String)

}