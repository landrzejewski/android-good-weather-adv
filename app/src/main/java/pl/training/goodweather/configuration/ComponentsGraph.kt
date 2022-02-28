package pl.training.goodweather.configuration

import dagger.Component
import pl.training.goodweather.forecast.ForecastModule
import pl.training.goodweather.forecast.adapter.view.ForecastViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ForecastModule::class])
interface ComponentsGraph {

    fun inject(forecastViewModel: ForecastViewModel)

}