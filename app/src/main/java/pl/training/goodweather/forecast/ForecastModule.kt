package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherApi
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherForecastProviderAdapter
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherForecastProviderMapper
import pl.training.goodweather.forecast.adapters.view.ForecastViewModelMapper
import pl.training.goodweather.forecast.ports.output.ForecastProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ForecastModule {

    companion object Values {

        const val CITY_KEY = "cityName"
        const val DEFAULT_CITY_NAME = "warsaw"

    }

    @Named("fake")
    @Singleton
    @Provides
    fun fakeForecastProvider(): ForecastProvider = FakeForecastProvider()

    @Singleton
    @Provides
    fun openWeatherApi(okHttpClient: OkHttpClient): OpenWeatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OpenWeatherApi::class.java)

    @Singleton
    @Provides
    fun openWeatherForecastProviderMapper(): OpenWeatherForecastProviderMapper = OpenWeatherForecastProviderMapper()

    @Named("openweather")
    @Singleton
    @Provides
    fun openWeatherProvider(openWeatherApi: OpenWeatherApi, mapper: OpenWeatherForecastProviderMapper, logger: Logger): ForecastProvider = OpenWeatherForecastProviderAdapter(openWeatherApi, mapper, logger)

    @Singleton
    @Provides
    fun forecastViewModelMapper(): ForecastViewModelMapper = ForecastViewModelMapper()

}