package pl.training.forecast.adapters.provider.openweather

import com.google.gson.annotations.SerializedName

data class ResponseTransferObject(
    @SerializedName("list") val forecast: List<DayForecastTransferObject>
)