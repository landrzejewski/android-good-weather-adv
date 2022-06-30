package pl.training.forecast.adapters.view

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.concat
import kotlinx.coroutines.rx3.rxObservable
import pl.training.forecast.adapters.view.ForecastIntent.RefreshForecast
import pl.training.forecast.adapters.view.ForecastResult.*
import pl.training.forecast.adapters.view.ForecastResult.Refreshed
import pl.training.forecast.adapters.view.ForecastViewState.*
import pl.training.forecast.ports.input.GetForecastUseCase
import pl.training.forecast.ports.model.DayForecast
import javax.inject.Inject

@HiltViewModel
internal class ForecastViewModel @Inject constructor(private val forecastService: GetForecastUseCase, private val mapper: ForecastViewModelMapper) : ViewModel() {

    var selectedDayForecastDate: String? = null

    fun process(intents: Observable<ForecastIntent>) = intents.flatMap { intent ->
        when (intent) {
            is RefreshForecast -> refreshForecast(intent)
        }
        .scan(Initial, this::reduce)
    }

    private fun reduce(state: ForecastViewState, result: ForecastResult) = when(result) {
        is Failure -> Error(result.message)
        is Refreshing -> Processing
        is Refreshed -> ForecastViewState.Refreshed(result.forecast)
    }

    private fun refreshForecast(intent: RefreshForecast): Observable<ForecastResult> {
        val refreshing = Observable.just(Refreshing)
        val forecast = rxObservable<List<DayForecast>> { forecastService.loadForecast(intent.cityName) }
            .map(mapper::toViewModel)
            .map { Refreshed(it) }
        return concat(refreshing, forecast)
    }

}