package pl.training.goodweather.forecast.adapter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.training.goodweather.databinding.FragmentForecastBinding

class ForecastFragment : Fragment() {

    private lateinit var binding: FragmentForecastBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentForecastBinding.inflate(inflater)
        return binding.root
    }

}