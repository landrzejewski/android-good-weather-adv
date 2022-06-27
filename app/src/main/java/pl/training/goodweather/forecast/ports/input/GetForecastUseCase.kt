package pl.training.goodweather.forecast.ports.input

import pl.training.goodweather.forecast.ports.model.DayForecast

interface GetForecastUseCase {

    suspend fun loadForecast(city: String): List<DayForecast>

}