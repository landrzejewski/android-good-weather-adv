package pl.training.profile.domain

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.training.profile.ports.model.Temperature

class GetTemperatureColorServiceTest {

    private val coldColor = "0000FF"
    private val hotColor = "FF0000"
    private val temperatureBoundary = 16.0

    @Test
    fun `given low temperature when get color then returns cold color`() = runTest {
        val getTemperatureColorService = GetTemperatureColorService { Temperature( temperatureBoundary - 1.0) }
        assertEquals(coldColor, getTemperatureColorService.get().hexValue)
    }

    @Test
    fun `given high temperature when get color then returns hot color`() = runTest {
        val getTemperatureColorService = GetTemperatureColorService { Temperature( temperatureBoundary + 1.0) }
        assertEquals(hotColor, getTemperatureColorService.get().hexValue)
    }

}