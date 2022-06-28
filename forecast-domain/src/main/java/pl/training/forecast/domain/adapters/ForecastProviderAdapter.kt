package pl.training.forecast.domain.adapters

import pl.training.forecast.ports.output.ForecastProvider

internal class ForecastProviderAdapter(private val forecastProvider: ForecastProvider, private val mapper: DayForecastDomainMapper) {

    suspend fun getForecast(city: String) = forecastProvider.getForecast(city).map(mapper::toDomain)

}