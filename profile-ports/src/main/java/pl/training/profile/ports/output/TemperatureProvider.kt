package pl.training.profile.ports.output

import pl.training.profile.ports.model.Temperature

fun interface TemperatureProvider {

    suspend fun get(): Temperature

}