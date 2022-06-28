package pl.training.goodweather.forecast.adapters.provider.openweather

import pl.training.goodweather.commons.logging.Logger
import pl.training.forecast.ports.output.ForecastProvider
import pl.training.forecast.ports.model.DayForecast
import java.lang.Exception

class OpenWeatherForecastProviderAdapter(private val openWeatherApi: OpenWeatherApi, private val mapper: OpenWeatherForecastProviderMapper, private val logger: Logger) :
    ForecastProvider {

    override suspend fun getForecast(city: String): List<DayForecast> {
        return try {
            openWeatherApi.getForecast(city).forecast.map(mapper::toModel)
        } catch (exception: Exception) {
            logger.log("Fetching forecast failed")
            return emptyList()
        }
    }

}