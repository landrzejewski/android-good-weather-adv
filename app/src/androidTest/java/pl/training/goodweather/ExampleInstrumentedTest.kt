package pl.training.goodweather

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private val preferencesName = "TEST_PREFERENCES"

    private fun getContext() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun useAppContext() {
        assertEquals("pl.training.goodweather", getContext().packageName)
    }

    @Test
    fun testSharedPreferences() {
        val key = "language"
        val value = "kotlin"
        val preferences = getContext().getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()

        val result = getContext().getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getString(key, "")
        assertEquals(value, result)
    }

}