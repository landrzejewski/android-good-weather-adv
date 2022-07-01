package pl.training.goodweather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import pl.training.forecast.adapters.R

class ForecastView {

    fun enterCityName(city: String) {
        onView(withId(R.id.cityNameEditText))
            .perform(replaceText(city), closeSoftKeyboard())
    }

    fun clickCheck() {
        onView(withId(R.id.checkButton))
            .perform(click())
    }

}