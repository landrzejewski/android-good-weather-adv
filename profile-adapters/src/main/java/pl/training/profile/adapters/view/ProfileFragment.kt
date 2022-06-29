package pl.training.profile.adapters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.training.commons.view.Point
import pl.training.profile.adapters.databinding.FragmentProfileBinding

internal class ProfileFragment: Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* viewModel.colorValue.observe(requireActivity()) {
            binding.color.text = it
        }*/
       /* GlobalScope.launch(Dispatchers.Main) {
            delay(5_000)
            binding.customButton.enable()
        }*/
    }

    override fun onResume() {
        super.onResume()
        binding.graph.draw(listOf(
            Point(0f, 0f),
            Point(1f, 8f),
            Point(2F, 3f),
            Point(3f, 7F),
            Point(4f, 9F),
            Point(5f, 4F)
        ))
        GlobalScope.launch(Dispatchers.Main) {
            delay(10_000)
            binding.graph.draw(listOf(
                Point(0f, 1f),
                Point(1f, 3f),
                Point(2F, 1f),
                Point(3f, 2F),
                Point(4f, 5F),
                Point(5f, 1F)
            ))
        }
    }

}