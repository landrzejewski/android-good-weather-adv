package pl.training.goodweather.forecast.adapter.persistence.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    @PrimaryKey var id: Long,
    val name: String
)