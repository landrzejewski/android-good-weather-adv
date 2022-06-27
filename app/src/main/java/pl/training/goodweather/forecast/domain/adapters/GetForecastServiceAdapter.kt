package pl.training.goodweather.forecast.domain.adapters

import pl.training.goodweather.forecast.domain.GetForecastService
import pl.training.goodweather.forecast.ports.input.GetForecastUseCase
import pl.training.goodweather.forecast.ports.model.DayForecast
import java.util.*

class GetForecastServiceAdapter(private val getForecastService: GetForecastService, private val mapper: DayForecastDomainMapper) : GetForecastUseCase {

    override suspend fun loadForecast(city: String) = getForecastService.loadForecast(city).map(mapper::toPort)

    override suspend fun loadCurrentForecast(): DayForecast {
        return  DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date())
    }

}