package pl.training.goodweather.configuration

import dagger.Component
import pl.training.goodweather.forecast.ForecastModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ForecastModule::class])
interface ComponentsGraph {
}