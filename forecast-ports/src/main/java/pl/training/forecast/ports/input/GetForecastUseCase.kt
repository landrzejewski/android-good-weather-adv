package pl.training.forecast.ports.input

import pl.training.forecast.ports.model.DayForecast

interface GetForecastUseCase {

    suspend fun loadForecast(city: String): List<DayForecast>

    suspend fun loadCurrentForecast(): DayForecast

}