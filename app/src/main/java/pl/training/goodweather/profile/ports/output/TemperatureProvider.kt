package pl.training.goodweather.profile.ports.output

import pl.training.goodweather.profile.ports.model.Temperature

interface TemperatureProvider {

    suspend fun get(): Temperature

}