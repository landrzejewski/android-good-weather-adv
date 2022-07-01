package pl.training.profile.adapters.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pl.training.profile.ports.input.GetTemperatureColorUseCase
import pl.training.profile.ports.model.Color
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class ProfileViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val color = "#efefefe"
    private val mapper = ProfileViewModelMapper()
    private val getTemperatureColorUseCase = object : GetTemperatureColorUseCase {

        override suspend fun get(): Color {
            return Color(color)
        }

    }

    @Test
    fun `when initialize then emit color`() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val viewModel = ProfileViewModel(getTemperatureColorUseCase, mapper)
        // assertEquals(color, viewModel.colorValue.value)
        viewModel.colorValue.getOrWait { assertEquals(color, it) }
    }

}


fun <T> LiveData<T>.getOrWait(time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS, callback: (T) -> Unit = {}): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object: Observer<T> {
        override fun onChanged(nextData: T) {
            data = nextData
            callback.invoke(nextData)
            latch.countDown()
            this@getOrWait.removeObserver(this)
        }
    }
    observeForever(observer)
    if (!latch.await(time, timeUnit)) {
        removeObserver(observer)
        throw TimeoutException("LiveData value was not found")
    }
    @Suppress("UNCHECKED_CAST")
    return data as T
}

