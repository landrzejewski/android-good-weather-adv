package pl.training.goodweather.profile

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.goodweather.profile.adapters.temperature.ForecastTemperatureProviderAdapter
import pl.training.goodweather.profile.adapters.temperature.TemperatureForecastMapper
import pl.training.profile.adapters.view.ProfileViewModelMapper
import pl.training.profile.domain.GetTemperatureColorService
import pl.training.profile.ports.input.GetTemperatureColorUseCase
import pl.training.profile.ports.output.TemperatureProvider

@Module
@InstallIn(SingletonComponent::class)
class ProfileModule {

    @Provides
    fun temperatureForecastMapper(): TemperatureForecastMapper = TemperatureForecastMapper()

    @Provides
    fun forecastTemperatureProviderAdapter(getForecastUseCase: GetForecastUseCase, mapper: TemperatureForecastMapper): TemperatureProvider
        = ForecastTemperatureProviderAdapter(getForecastUseCase, mapper)

    @Provides
    fun getTemperatureColorService(temperatureProvider: TemperatureProvider): GetTemperatureColorUseCase = GetTemperatureColorService(temperatureProvider)

    @Provides
    fun profileViewModelMapper(): ProfileViewModelMapper = ProfileViewModelMapper()

}