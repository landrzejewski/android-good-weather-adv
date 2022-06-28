package pl.training.forecast.adapters.view

data class DayForecastViewModel(
    val icon: String,
    val description: String,
    var temperature: String,
    val pressure: String,
    val date: String
)