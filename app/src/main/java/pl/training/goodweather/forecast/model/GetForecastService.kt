package pl.training.goodweather.forecast.model

import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.api.GetForecastUseCase

class GetForecastService(private val forecastProvider: ForecastProvider, private val forecastRepository: ForecastRepository) : GetForecastUseCase {

    override suspend fun loadForecast(city: String): List<DayForecast> {
        val forecast = forecastProvider.getForecast(city)
        forecastRepository.clear()
        forecastRepository.save(city, forecast)
        return forecast
    }

    override suspend fun getCachedForecast(city: String) = forecastRepository.load(city)

}