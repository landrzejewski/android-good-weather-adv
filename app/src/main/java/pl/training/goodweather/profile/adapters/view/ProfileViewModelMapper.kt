package pl.training.goodweather.profile.adapters.view

import pl.training.profile.ports.model.Color

class ProfileViewModelMapper {

    fun toViewModel(color: Color) = color.hexValue

}