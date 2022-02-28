package pl.training.goodweather.forecast.adapter.logging

import pl.training.goodweather.commons.logging.Logger

class GetForecastLogger(private val logger: Logger) {

    fun logForecastLoading(city: String) {
        logger.log("Loading forecast for city $city")
    }

    fun logForecastReadingFormCache(city: String) {
        logger.log("Loading forecast for city $city from cache")
    }

}