package pl.training.forecast.domain.adapters

import pl.training.forecast.domain.GetForecastService
import pl.training.forecast.ports.ForecastFactory
import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.forecast.ports.output.ForecastProvider

class DefaultForecastFactory: ForecastFactory {

    private val dayForecastDomainMapper = DayForecastDomainMapper()

    override fun create(forecastProvider: ForecastProvider): GetForecastUseCase {
       val forecastProviderAdapter = ForecastProviderAdapter(forecastProvider, dayForecastDomainMapper)
       val getForecastService = GetForecastService(forecastProviderAdapter)
       return GetForecastServiceAdapter(getForecastService, dayForecastDomainMapper);
    }

}