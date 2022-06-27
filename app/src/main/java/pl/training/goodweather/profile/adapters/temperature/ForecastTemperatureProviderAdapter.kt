package pl.training.goodweather.profile.adapters.temperature

import pl.training.goodweather.forecast.ports.input.GetForecastUseCase
import pl.training.goodweather.forecast.ports.model.TemperatureLoadFailedException
import pl.training.goodweather.profile.ports.model.Temperature
import pl.training.goodweather.profile.ports.output.TemperatureProvider

class ForecastTemperatureProviderAdapter(private val getForecastUseCase: GetForecastUseCase, private val mapper: TemperatureForecastMapper): TemperatureProvider {

    override suspend fun get(): Temperature {
        return try {
            mapper.toPort(getForecastUseCase.loadCurrentForecast())
        } catch (exception: Exception) {
            throw TemperatureLoadFailedException()
        }
    }

}