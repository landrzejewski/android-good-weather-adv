package pl.training.goodweather.profile.adapters.temperature

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.forecast.ports.model.DayForecast
import pl.training.profile.ports.model.Temperature
import java.util.*

class ForecastTemperatureProviderAdapterTest {

    private val temperature = Temperature(18.0)
    private val mapper = TemperatureForecastMapper()
    private lateinit var getForecastUseCase: GetForecastUseCase

    @Before
    fun beforeEach() {
        getForecastUseCase = mock()
        getForecastUseCase.stub {
            onBlocking { loadCurrentForecast() }.thenReturn(DayForecast("ic_sun", "Clear sky", temperature.value, 1024.0, Date()))
        }
    }

    @Test
    fun `given current forecast when get temperature then returns current temperature`() = runTest {
        val temperatureProviderAdapter = ForecastTemperatureProviderAdapter(getForecastUseCase, mapper)
        assertEquals(temperature.value, temperatureProviderAdapter.get().value, 0.1)
    }

}