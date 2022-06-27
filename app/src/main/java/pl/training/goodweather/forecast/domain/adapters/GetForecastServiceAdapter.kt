package pl.training.goodweather.forecast.domain.adapters

import pl.training.goodweather.forecast.domain.GetForecastService
import pl.training.goodweather.forecast.ports.input.GetForecastUseCase

class GetForecastServiceAdapter(private val getForecastService: GetForecastService, private val mapper: DayForecastDomainMapper) : GetForecastUseCase {

    override suspend fun loadForecast(city: String) = getForecastService.loadForecast(city).map(mapper::toPort)

}