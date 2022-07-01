package pl.training.goodweather.configuration

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.training.forecast.adapters.persistence.CityEntity
import pl.training.forecast.adapters.persistence.DayForecastEntity
import pl.training.forecast.adapters.persistence.ForecastDao

@Database(entities = [CityEntity::class, DayForecastEntity::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

}