package pl.training.profile.adapters.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.profile.ports.input.GetTemperatureColorUseCase
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(private val getTemperatureColorUseCase: GetTemperatureColorUseCase, private val mapper: ProfileViewModelMapper): ViewModel() {

    internal var colorValue = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            val color = getTemperatureColorUseCase.get()
            colorValue.postValue(mapper.toViewModel(color))
        }
    }

}