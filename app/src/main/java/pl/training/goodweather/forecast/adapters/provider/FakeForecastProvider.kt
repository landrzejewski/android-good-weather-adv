package pl.training.goodweather.forecast.adapters.provider

import pl.training.goodweather.forecast.ports.output.ForecastProvider
import pl.training.goodweather.forecast.ports.model.DayForecast
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