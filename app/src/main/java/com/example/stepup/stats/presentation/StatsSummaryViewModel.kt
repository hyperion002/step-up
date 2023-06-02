package com.example.stepup.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.stepup.StepUpApplication
import com.example.stepup.core.data.repository.DayRepositoryImpl
import com.example.stepup.stats.domain.usecase.StatsSummaryUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StatsSummaryViewModel(
    private val statsSummaryUseCases: StatsSummaryUseCases
) : ViewModel() {

    private val _statsSummary = MutableStateFlow(StatsSummaryState())
    val statsSummary: StateFlow<StatsSummaryState> = _statsSummary.asStateFlow()

    init {
        refreshStatsSummary()
    }

    private var refreshStatsSummaryJob: Job? = null
    fun refreshStatsSummary() {
        refreshStatsSummaryJob?.cancel()
        refreshStatsSummaryJob = viewModelScope.launch {
            _statsSummary.value = statsSummary.value.copy(
                isRefreshing = true
            )
            val updatedSummary = statsSummaryUseCases.getSummary()
            updatedSummary.run {
                _statsSummary.value = statsSummary.value.copy(
                    isRefreshing = false,
                    stepsTaken = stepsTaken,
                    calorieBurned = calorieBurned,
                    distanceTravelled = distanceTravelled,
                    carbonDioxideSaved = carbonDioxideSaved
                )
            }
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[APPLICATION_KEY]) as StepUpApplication

            val dayRepository = DayRepositoryImpl(application.stepUpDatabase.dayDao)
            val statsSummaryUseCases = StatsSummaryUseCases(dayRepository)

            return StatsSummaryViewModel(statsSummaryUseCases) as T
        }
    }
}