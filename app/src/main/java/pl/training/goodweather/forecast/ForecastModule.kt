package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.training.forecast.domain.adapters.DefaultForecastFactory
import pl.training.commons.logging.Logger
import pl.training.forecast.adapters.provider.FakeForecastProvider
import pl.training.forecast.adapters.provider.openweather.OpenWeatherApi
import pl.training.forecast.adapters.provider.openweather.OpenWeatherForecastProviderAdapter
import pl.training.forecast.adapters.provider.openweather.OpenWeatherForecastProviderMapper
import pl.training.forecast.adapters.view.ForecastViewModelMapper
import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.forecast.ports.output.ForecastProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForecastModule {

    @Fake
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

    @OpenWeather
    @Singleton
    @Provides
    fun openWeatherProvider(openWeatherApi: OpenWeatherApi, mapper: OpenWeatherForecastProviderMapper, logger: Logger): ForecastProvider = OpenWeatherForecastProviderAdapter(openWeatherApi, mapper, logger)

    @Singleton
    @Provides
    fun forecastViewModelMapper(): ForecastViewModelMapper = ForecastViewModelMapper()

    @Provides
    fun getForecastUseCase(@OpenWeather forecastProvider: ForecastProvider): GetForecastUseCase = DefaultForecastFactory().create(forecastProvider)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fake

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeather