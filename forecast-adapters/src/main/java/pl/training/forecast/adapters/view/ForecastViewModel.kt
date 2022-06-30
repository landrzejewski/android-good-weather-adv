package pl.training.forecast.adapters.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import pl.training.forecast.adapters.view.ForecastIntent.RefreshForecast
import pl.training.forecast.adapters.view.ForecastResult.*
import pl.training.forecast.adapters.view.ForecastResult.Refreshed
import pl.training.forecast.adapters.view.ForecastViewState.*
import pl.training.forecast.ports.input.GetForecastUseCase
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(private val forecastService: GetForecastUseCase, private val mapper: ForecastViewModelMapper) : ViewModel() {

    var selectedDayForecastDate: String? = null

    private val cache = MutableSharedFlow<ForecastResult>()

    fun process(intents: Flow<ForecastIntent>): Flow<ForecastViewState> {
        viewModelScope.launch {
            intents.flatMapMerge {
                when (it) {
                    is RefreshForecast -> refreshForecast(it)
                }
            }
            .collect { cache.emit(it) }
        }
        return cache.scan(Initial, this::reduce)
    }

    private fun reduce(state: ForecastViewState, result: ForecastResult) = when (result) {
        is Failure -> Error(result.message)
        is Refreshing -> Processing
        is Refreshed -> ForecastViewState.Refreshed(result.forecast)
    }

    private fun refreshForecast(intent: RefreshForecast) = flow {
        emit(Refreshing)
        val refreshed = Refreshed(mapper.toViewModel(forecastService.loadForecast(intent.cityName)))
        emit(refreshed)
    }

}