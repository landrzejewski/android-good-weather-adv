package pl.training.forecast.adapters.view

sealed class ForecastViewState {

    object Initial: ForecastViewState()

    data class Error(val message: String): ForecastViewState()

    object Processing: ForecastViewState()

    data class Refreshed(val forecast: List<DayForecastViewModel>): ForecastViewState()

}