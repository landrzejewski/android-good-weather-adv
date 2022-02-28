package pl.training.goodweather.forecast.adapter.persistence.room

import androidx.room.Embedded
import androidx.room.Relation

data class ForecastAggregate(
    @Embedded val city: CityEntity,
    @Relation(parentColumn = "id", entityColumn = "cityId") val forecast: List<DayForecastEntity>
)