package pl.training.forecast.adapters.view

sealed class ForecastIntent {

    data class RefreshForecast(val cityName: String): ForecastIntent()

}