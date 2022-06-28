package pl.training.profile.domain

import pl.training.profile.ports.input.GetTemperatureColorUseCase
import pl.training.profile.ports.model.Color
import pl.training.profile.ports.output.TemperatureProvider

class GetTemperatureColorService(private val temperatureProvider: TemperatureProvider): GetTemperatureColorUseCase {

    private val minTemperature = 16
    private val coldColor = "0000FF"
    private val hotColor = "FF0000"

    override suspend fun get(): Color {
        val colorValue = if (temperatureProvider.get().value > minTemperature) hotColor else coldColor
        return Color(colorValue)
    }

}