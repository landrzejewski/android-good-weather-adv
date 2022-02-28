package pl.training.goodweather.forecast.adapter.logging

import pl.training.goodweather.forecast.api.GetForecastUseCase
import pl.training.goodweather.forecast.model.DayForecast

class GetForecastUseCaseLoggingProxy(private val getForecastUseCase: GetForecastUseCase, private val getForecastLogger: GetForecastLogger): GetForecastUseCase {

    override suspend fun loadForecast(city: String): List<DayForecast> {
        getForecastLogger.logForecastLoading(city)
        return getForecastUseCase.loadForecast(city)
    }

    override suspend fun getCachedForecast(city: String): List<DayForecast> {
        getForecastLogger.logForecastReadingFormCache(city)
        return getForecastUseCase.getCachedForecast(city)
    }

}