package pl.training.goodweather.forecast

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.configuration.ApplicationDatabase
import pl.training.goodweather.forecast.adapter.logging.GetForecastLogger
import pl.training.goodweather.forecast.adapter.logging.GetForecastUseCaseLoggingProxy
import pl.training.goodweather.forecast.adapter.persistence.room.ForecastDao
import pl.training.goodweather.forecast.adapter.persistence.room.RoomForecastRepositoryAdapter
import pl.training.goodweather.forecast.adapter.persistence.room.RoomForecastRepositoryMapper
import pl.training.goodweather.forecast.adapter.provider.FakeForecastProvider
import pl.training.goodweather.forecast.adapter.provider.openweather.OpenWeatherApi
import pl.training.goodweather.forecast.adapter.provider.openweather.OpenWeatherForecastProviderAdapter
import pl.training.goodweather.forecast.adapter.provider.openweather.OpenWeatherForecastProviderMapper
import pl.training.goodweather.forecast.adapter.view.ForecastViewModelMapper
import pl.training.goodweather.forecast.api.ForecastProvider
import pl.training.goodweather.forecast.api.ForecastRepository
import pl.training.goodweather.forecast.api.GetForecastUseCase
import pl.training.goodweather.forecast.model.GetForecastService
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
    fun forecastDao(database: ApplicationDatabase): ForecastDao = database.forecastDao()

    @Singleton
    @Provides
    fun roomForecastRepositoryMapper(): RoomForecastRepositoryMapper = RoomForecastRepositoryMapper()

    @Singleton
    @Provides
    fun roomForecastRepository(forecastDao: ForecastDao, mapper: RoomForecastRepositoryMapper): ForecastRepository = RoomForecastRepositoryAdapter(forecastDao, mapper)

    @Named("service")
    @Singleton
    @Provides
    fun getForecastService(@Named("openweather") forecastProvider: ForecastProvider, forecastRepository: ForecastRepository): GetForecastUseCase = GetForecastService(forecastProvider, forecastRepository)

    @Singleton
    @Provides
    fun forecastViewModelMapper(): ForecastViewModelMapper = ForecastViewModelMapper()

    @Singleton
    @Provides
    fun getForecastLogger(logger: Logger): GetForecastLogger = GetForecastLogger(logger)

    @Named("proxy")
    @Singleton
    @Provides
    fun getForecastServiceLoggingProxy(@Named("service") getForecastService: GetForecastUseCase, getForecastLogger: GetForecastLogger): GetForecastUseCase = GetForecastUseCaseLoggingProxy(getForecastService, getForecastLogger)

}