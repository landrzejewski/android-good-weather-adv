package pl.training.forecast.adapters.view

sealed class ForecastResult {

    data class Refreshed(val forecast: List<DayForecastViewModel>): ForecastResult()

    object Refreshing: ForecastResult()

    data class Failure(val message: String): ForecastResult()

}