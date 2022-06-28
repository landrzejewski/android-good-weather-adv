package pl.training.forecast.adapters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.training.forecast.adapters.databinding.FragmentDayForecastBinding

internal class DayForecastFragment : Fragment() {

    private lateinit var binding: FragmentDayForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDayForecastBinding.inflate(inflater)
        return binding.root
    }

}