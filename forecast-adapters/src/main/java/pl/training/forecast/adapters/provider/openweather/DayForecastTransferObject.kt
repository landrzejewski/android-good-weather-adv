package pl.training.forecast.adapters.provider.openweather

import com.google.gson.annotations.SerializedName

data class DayForecastTransferObject(
    @SerializedName("temp") val temperature: TemperatureTransferObject,
    val pressure: Double,
    @SerializedName("dt") val date: Long,
    val weather: List<WeatherTransferObject>
)