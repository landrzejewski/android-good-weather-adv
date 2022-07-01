package pl.training.forecast.adapters.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class DayForecastEntity(
    @PrimaryKey(autoGenerate = false) var id: Long?,
    val icon: String,
    val description: String,
    val temperature: Double,
    var pressure: Double,
    val date: Long,
    val cityId: Long
)