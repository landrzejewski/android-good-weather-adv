package pl.training.forecast.adapters.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ForecastDao {

    @Insert(onConflict = REPLACE)
    suspend fun save(cityEntity: CityEntity)

    @Insert
    suspend fun save(forecast: List<DayForecastEntity>)

    @Transaction
    @Query("select * from CityEntity where name = :city")
    suspend fun findByCity(city: String): ForecastAggregate

    @Query("delete from DayForecastEntity")
    suspend fun deleteAll()

}