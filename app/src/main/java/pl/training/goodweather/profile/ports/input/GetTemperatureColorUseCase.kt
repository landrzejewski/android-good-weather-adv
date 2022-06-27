package pl.training.goodweather.profile.ports.input

import pl.training.goodweather.profile.ports.model.Color

interface GetTemperatureColorUseCase {

    suspend fun get(): Color

}