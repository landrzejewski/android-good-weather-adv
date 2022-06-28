package pl.training.forecast.domain.adapters

import pl.training.forecast.domain.DayForecastDomain
import pl.training.forecast.ports.model.DayForecast

internal class DayForecastDomainMapper {

    fun toPort(dayForecastDomain: DayForecastDomain) = DayForecast(
        dayForecastDomain.icon,
        dayForecastDomain.description,
        dayForecastDomain.temperature,
        dayForecastDomain.pressure,
        dayForecastDomain.date
    )

    fun toDomain(dayForecast: DayForecast) = DayForecastDomain(
        dayForecast.icon,
        dayForecast.description,
        dayForecast.temperature,
        dayForecast.pressure,
        dayForecast.date
    )

}