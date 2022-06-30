package pl.training.forecast.adapters.view

import pl.training.commons.formatDate
import pl.training.commons.formatPressure
import pl.training.commons.formatTemperature
import pl.training.forecast.ports.model.DayForecast

class ForecastViewModelMapper {

    fun toViewModel(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastViewModel(icon, description, formatTemperature(temperature), formatPressure(pressure), formatDate(date))
    }

    fun toViewModel(dayForecastList: List<DayForecast>) = dayForecastList.map { toViewModel(it) }

}