package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.forecast.adapters.provider.FakeForecastProvider
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherApi
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherForecastProviderAdapter
import pl.training.goodweather.forecast.adapters.provider.openweather.OpenWeatherForecastProviderMapper
import pl.training.goodweather.forecast.adapters.view.ForecastViewModelMapper
import pl.training.goodweather.forecast.domain.GetForecastService
import pl.training.goodweather.forecast.domain.adapters.DayForecastDomainMapper
import pl.training.goodweather.forecast.domain.adapters.ForecastProviderAdapter
import pl.training.goodweather.forecast.domain.adapters.GetForecastServiceAdapter
import pl.training.goodweather.forecast.ports.input.GetForecastUseCase
import pl.training.goodweather.forecast.ports.output.ForecastProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ForecastModule {

    companion object Values {

        const val CITY_KEY = "cityName"
        const val DEFAULT_CITY_NAME = "warsaw"

    }

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
    fun dayForecastDomainMapper(): DayForecastDomainMapper = DayForecastDomainMapper()

    @Provides
    fun forecastProviderAdapter(@OpenWeather forecastProvider: ForecastProvider, mapper: DayForecastDomainMapper): ForecastProviderAdapter = ForecastProviderAdapter(forecastProvider, mapper);

    @Provides
    fun getForecastService(forecastProviderAdapter: ForecastProviderAdapter): GetForecastService = GetForecastService(forecastProviderAdapter)

    @Provides
    fun getForecastUseCase(getForecastService: GetForecastService, mapper: DayForecastDomainMapper): GetForecastUseCase = GetForecastServiceAdapter(getForecastService, mapper)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Fake

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeather