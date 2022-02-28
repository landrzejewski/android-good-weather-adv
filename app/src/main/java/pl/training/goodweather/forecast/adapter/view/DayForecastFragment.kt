package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.training.goodweather.databinding.FragmentDayForecastBinding

class DayForecastFragment : Fragment() {

    private lateinit var binding: FragmentDayForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDayForecastBinding.inflate(inflater)
        return binding.root
    }

}