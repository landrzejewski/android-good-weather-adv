package pl.training.profile.ports.output

import pl.training.profile.ports.model.Temperature

interface TemperatureProvider {

    suspend fun get(): Temperature

}