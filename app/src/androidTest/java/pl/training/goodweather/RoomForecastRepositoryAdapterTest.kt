package pl.training.goodweather

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.training.forecast.adapters.persistence.*
import pl.training.forecast.ports.model.DayForecast
import pl.training.goodweather.configuration.ApplicationDatabase
import java.util.*

@RunWith(AndroidJUnit4::class)
class RoomForecastRepositoryAdapterTest {

    private val cityEntity = CityEntity(1L, "London")
    private val forecast = listOf(DayForecastEntity(null, "icon", "desc", 12.0, 1000.0, 1000, cityEntity.id))
    private val expectedForecast = listOf(DayForecast("icon", "desc", 12.0, 1000.0, Date(1000)))

    private val mapper = RoomForecastRepositoryMapper()
    private lateinit var forecastDao: ForecastDao
    private lateinit var forecastRepository: RoomForecastRepositoryAdapter

    @Before
    fun beforeEach() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        forecastDao = Room.inMemoryDatabaseBuilder(context, ApplicationDatabase::class.java).build().forecastDao()
        forecastRepository = RoomForecastRepositoryAdapter(forecastDao, mapper)
    }

    @Test
    fun given_persisted_forecast_when_load_by_city_then_returns_the_forecast() = runTest(StandardTestDispatcher()) {
        forecastDao.save(cityEntity)
        forecastDao.save(forecast)
        assertEquals(expectedForecast, forecastRepository.load(cityEntity.name))
    }

}