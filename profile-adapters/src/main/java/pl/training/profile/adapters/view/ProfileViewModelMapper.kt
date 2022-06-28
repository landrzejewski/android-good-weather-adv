package pl.training.profile.adapters.view

import pl.training.profile.ports.model.Color

class ProfileViewModelMapper {

    fun toViewModel(color: Color) = color.hexValue

}