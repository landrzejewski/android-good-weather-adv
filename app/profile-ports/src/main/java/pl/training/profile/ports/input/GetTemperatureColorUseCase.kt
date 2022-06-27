package pl.training.profile.ports.input

import pl.training.profile.ports.model.Color

interface GetTemperatureColorUseCase {

    suspend fun get(): Color

}