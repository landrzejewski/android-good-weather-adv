package pl.training.forecast.adapters.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.training.forecast.adapters.R
import pl.training.forecast.adapters.databinding.ItemDayForecastBinding
import pl.training.commons.setDrawable

internal class ForecastListAdapter(private var forecast: List<DayForecastViewModel> = emptyList(), var tapListener: (DayForecastViewModel) -> Unit = {}) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    class ViewHolder(view: View, var tapListener: (DayForecastViewModel) -> Unit) : RecyclerView.ViewHolder(view) {

        private val binding = ItemDayForecastBinding.bind(view)

        fun update(dayForecastViewModel: DayForecastViewModel) {
            binding.root.setOnClickListener { tapListener(dayForecastViewModel) }
            binding.iconImageView.setDrawable(dayForecastViewModel.icon)
            binding.temperatureTextView.text = dayForecastViewModel.temperature
            binding.dateTextView.text = dayForecastViewModel.date
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_day_forecast, parent, false)
        return ViewHolder(view, tapListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.update(forecast[position])
    }

    override fun getItemCount() = forecast.size

    fun update(forecast: List<DayForecastViewModel>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

}