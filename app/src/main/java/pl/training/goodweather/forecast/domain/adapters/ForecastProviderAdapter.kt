package pl.training.goodweather.forecast.domain.adapters

import pl.training.goodweather.forecast.ports.output.ForecastProvider

class ForecastProviderAdapter(private val forecastProvider: ForecastProvider, private val mapper: DayForecastDomainMapper) {

    suspend fun getForecast(city: String) = forecastProvider.getForecast(city).map(mapper::toDomain)

}