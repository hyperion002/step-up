package com.example.stepup.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.stepup.StepUpApplication
import com.example.stepup.core.data.repository.DayRepositoryImpl
import com.example.stepup.core.domain.model.Day
import com.example.stepup.stats.domain.usecase.StatsChartPageUseCases
import com.example.stepup.stats.util.alignWeek
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatsChartPageViewModel(
    private val statsCharPageUseCases: StatsChartPageUseCases
) : ViewModel() {

    private val _week = MutableStateFlow<List<Day>>(emptyList())
    val week: StateFlow<List<Day>> = _week.asStateFlow()

    private var getWeekJob: Job? = null

    fun selectWeek(firstDate: LocalDate) {
        getWeekJob?.cancel()
        getWeekJob = viewModelScope.launch {
            statsCharPageUseCases.getWeek(firstDate).collect { week ->
                _week.value = week.alignWeek(firstDate)
            }
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[APPLICATION_KEY]) as StepUpApplication

            val dayRepository = DayRepositoryImpl(application.stepUpDatabase.dayDao)
            val statsChartPageUseCases = StatsChartPageUseCases(dayRepository)

            return StatsChartPageViewModel(statsChartPageUseCases) as T
        }
    }
}