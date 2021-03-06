package pl.training.goodweather.forecast.adapters.provider.openweather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("forecast/daily?units=metric&appid=b933866e6489f58987b2898c89f542b8")
    suspend fun getForecast(@Query("q") city: String) : ResponseTransferObject

}