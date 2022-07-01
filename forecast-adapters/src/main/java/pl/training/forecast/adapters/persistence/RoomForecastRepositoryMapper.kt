package pl.training.forecast.adapters.persistence

import pl.training.forecast.ports.model.DayForecast
import java.util.*

class RoomForecastRepositoryMapper {

    companion object {

        const val CITY_ID = 1L

    }

    fun toEntity(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastEntity(null, icon, description, temperature, pressure, date.time, CITY_ID)
    }

    fun toPorts(dayForecastEntity: DayForecastEntity) = with(dayForecastEntity) {
        DayForecast(icon, description, temperature, pressure, Date(date))
    }

}