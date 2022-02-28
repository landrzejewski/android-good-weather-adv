package pl.training.goodweather.forecast.adapter.persistence.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DayForecastEntity(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    val icon: String,
    val description: String,
    val temperature: Double,
    val pressure: Double,
    val date: Long,
    val cityId: Long
)