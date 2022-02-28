package pl.training.goodweather.forecast.adapter.persistence.room

import pl.training.goodweather.forecast.model.DayForecast
import java.util.*

class RoomForecastRepositoryMapper {

    companion object {

        const val CITY_ID = 1L

    }

    fun toEntity(dayForecast: DayForecast) = with(dayForecast) {
        DayForecastEntity(null, icon, description, temperature, pressure, date.time, CITY_ID)
    }

    fun toModel(dayForecastEntity: DayForecastEntity) = with(dayForecastEntity) {
        DayForecast(icon, description, temperature, pressure, Date(date))
    }

}