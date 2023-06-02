package com.example.stepup.stats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.stepup.R
import com.example.stepup.databinding.FragmentStatsSummaryBinding
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class StatsSummaryFragment : Fragment() {
    private var _binding: FragmentStatsSummaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatsSummaryViewModel by viewModels { StatsSummaryViewModel }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.swipeRefreshContainer.setOnRefreshListener {
            viewModel.refreshStatsSummary()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.statsSummary.collect { updateUserInterface(it) }
            }
        }
    }

    private fun updateUserInterface(state: StatsSummaryState) = state.apply {
        val stepsText = resources.getQuantityString(R.plurals.step_count_format, stepsTaken.toInt(), stepsTaken)
        val calorieText = getString(R.string.calorie_burned_format, calorieBurned.roundToInt())
        val distanceText = getString(R.string.distance_travelled_format, distanceTravelled)
        val carbonDioxideText = getString(R.string.carbon_dioxide_saved_format, carbonDioxideSaved)
        binding.apply {
            swipeRefreshContainer.isRefreshing = state.isRefreshing
            textStepCount.text = stepsText
            textCalorieBurned.text = calorieText
            textDistanceTravelled.text = distanceText
            textCarbonDioxideSaved.text = carbonDioxideText
        }
    }
}