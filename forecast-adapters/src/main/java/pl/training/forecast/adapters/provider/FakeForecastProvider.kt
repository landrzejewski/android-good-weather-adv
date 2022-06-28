package pl.training.forecast.adapters.provider

import pl.training.forecast.ports.output.ForecastProvider
import pl.training.forecast.ports.model.DayForecast
import java.util.*

class FakeForecastProvider : ForecastProvider {

    override suspend fun getForecast(city: String) = listOf(
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date()),
        DayForecast("ic_sun", "Clear sky", 18.0, 1024.0, Date())
    )

}