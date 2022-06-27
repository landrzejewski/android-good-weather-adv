package pl.training.goodweather.forecast.domain

import pl.training.goodweather.forecast.domain.adapters.ForecastProviderAdapter

class GetForecastService(private val forecastProvider: ForecastProviderAdapter) {

    suspend fun loadForecast(city: String) = forecastProvider.getForecast(city)

}