package pl.training.forecast.domain

import pl.training.forecast.domain.adapters.ForecastProviderAdapter

internal class GetForecastService(private val forecastProvider: ForecastProviderAdapter) {

    suspend fun loadForecast(city: String) = forecastProvider.getForecast(city)

}