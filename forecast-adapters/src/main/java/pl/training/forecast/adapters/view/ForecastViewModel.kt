package pl.training.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.training.forecast.ports.input.GetForecastUseCase
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(private val forecastService: GetForecastUseCase, private val mapper: ForecastViewModelMapper) : ViewModel() {

    private val forecastData = MutableLiveData<List<DayForecastViewModel>>()

    val forecast: LiveData<List<DayForecastViewModel>> = forecastData
    var selectedDayForecastDate: String? = null

    fun refreshForecast(city: String) {
        viewModelScope.launch {
            onForecastLoaded(forecastService.loadForecast(city).map(mapper::toViewModel))
        }
    }

    private fun onForecastLoaded(forecast: List<DayForecastViewModel>) {
        if (forecast.isNotEmpty()) {
            forecastData.postValue(forecast)
        }
    }

}