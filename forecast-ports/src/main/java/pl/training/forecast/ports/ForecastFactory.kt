package pl.training.forecast.ports

import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.forecast.ports.output.ForecastProvider

interface ForecastFactory {

    fun create(forecastProvider: ForecastProvider): GetForecastUseCase

}