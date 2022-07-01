package pl.training.forecast.adapters.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.training.commons.getProperty
import pl.training.commons.hideKeyboard
import pl.training.commons.setDrawable
import pl.training.commons.setProperty
import pl.training.forecast.adapters.R
import pl.training.forecast.adapters.databinding.FragmentForecastBinding
import pl.training.forecast.adapters.view.ForecastIntent.RefreshForecast
import pl.training.forecast.adapters.view.ForecastViewState.*

class ForecastFragment : Fragment() {

    companion object Values {

        const val CITY_KEY = "cityName"
        const val DEFAULT_CITY_NAME = "warsaw"

    }

    private lateinit var binding: FragmentForecastBinding
    private val viewModel: ForecastViewModel by activityViewModels()
    private val forecastListAdapter = ForecastListAdapter()
    private val disposables = CompositeDisposable()
    private val job = Job()
    private val fragmentScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentForecastBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProperty(CITY_KEY, DEFAULT_CITY_NAME)?.let { binding.cityNameEditText.setText(it) }
        initViews()
        bindViews()
    }

    private fun initViews() {
        binding.forecastRecyclerView.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        binding.forecastRecyclerView.adapter = forecastListAdapter
    }

    private fun bindViews() {
        val cityChanges = binding.cityNameEditText.textChanges()
            .map { it.toString() }

        val clicks = binding.checkButton.clicks()

        val initialCity: Observable<String> = Observable.just(binding.cityNameEditText.text)
            .map { it.toString() }

        val refreshIntents = clicks.withLatestFrom(cityChanges) { _, city -> city }
            .startWith(initialCity)
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .map { RefreshForecast(it) }
            .share()

        val flow = MutableSharedFlow<ForecastIntent>()

        fragmentScope.launch {
            viewModel.process(flow).collect {
                render(it)
            }
        }

        refreshIntents.subscribe {
            binding.cityNameEditText.hideKeyboard()
            setProperty(CITY_KEY, it.cityName)
            fragmentScope.launch { flow.emit(it) }
        }
        .addTo(disposables)

        binding.iconImage.setOnClickListener {
            findNavController().navigate(R.id.show_day_forecast_details)
        }

        forecastListAdapter.tapListener = {
            viewModel.selectedDayForecastDate = it.date
            findNavController().navigate(R.id.show_day_forecast_details)
        }
    }

    private fun render(viewState: ForecastViewState) = when(viewState) {
        is Initial -> {}
        is Refreshed -> render(viewState.forecast)
        is Error -> Log.i("###", "Error: ${viewState.message}")
        is Processing -> Log.i("###", "Processing...")
    }

    private fun render(forecast: List<DayForecastViewModel>) {
        with(forecast.first()) {
            viewModel.selectedDayForecastDate = date
            binding.iconImage.setDrawable(icon)
            binding.descriptionTextView.text = description
            binding.temperatureTextView.text = temperature
            binding.pressureTextView.text = pressure
        }
        forecastListAdapter.update(forecast.drop(1))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        job.cancel()
    }

}