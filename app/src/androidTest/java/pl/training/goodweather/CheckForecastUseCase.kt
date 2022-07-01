package pl.training.goodweather

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckForecastUseCase {

    @get:Rule
    var mainActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun check_forecast_for_city() = with(ForecastView()) {
        enterCityName("London")
        clickCheck()
    }

}