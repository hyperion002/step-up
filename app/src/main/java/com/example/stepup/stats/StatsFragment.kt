package com.example.stepup.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stepup.R
import com.example.stepup.databinding.FragmentStatsBinding
import com.example.stepup.stats.presentation.StatsDetailsFragment
import com.example.stepup.stats.presentation.StatsSummaryFragment
import com.google.android.material.tabs.TabLayoutMediator

class StatsFragment : Fragment() {
    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val fragments = listOf(
            R.string.details to { StatsDetailsFragment() },
            R.string.summary to { StatsSummaryFragment() }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val statsPageAdapter = StatsPageAdapter(this)
        binding.pager.adapter = statsPageAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            val tabTitleRes = fragments[position].first
            tab.text = getString(tabTitleRes)
            tab.setContentDescription(tabTitleRes)
        }.attach()
    }

    class StatsPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position].second()
    }
}