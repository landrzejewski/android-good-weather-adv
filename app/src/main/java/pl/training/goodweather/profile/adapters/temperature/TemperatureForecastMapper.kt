package pl.training.goodweather.profile.adapters.temperature

import pl.training.goodweather.forecast.ports.model.DayForecast
import pl.training.profile.ports.model.Temperature

class TemperatureForecastMapper {

    fun toPort(dayForecast: DayForecast) = Temperature(dayForecast.temperature)

}