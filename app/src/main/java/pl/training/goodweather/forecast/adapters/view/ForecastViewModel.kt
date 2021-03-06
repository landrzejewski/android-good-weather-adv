package pl.training.goodweather.forecast.adapters.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.forecast.ports.input.GetForecastUseCase
import javax.inject.Inject

class ForecastViewModel : ViewModel() {

    @Inject
    lateinit var forecastService: GetForecastUseCase
    @Inject
    lateinit var mapper: ForecastViewModelMapper

    init {
        componentsGraph.inject(this)
    }

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