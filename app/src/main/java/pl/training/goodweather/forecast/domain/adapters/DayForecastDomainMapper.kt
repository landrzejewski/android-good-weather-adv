package pl.training.goodweather.forecast.domain.adapters

import pl.training.goodweather.forecast.domain.DayForecastDomain
import pl.training.goodweather.forecast.ports.model.DayForecast

class DayForecastDomainMapper {

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