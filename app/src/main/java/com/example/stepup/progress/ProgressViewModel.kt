package com.example.stepup.progress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.stepup.StepUpApplication
import com.example.stepup.core.data.repository.DayRepositoryImpl
import com.example.stepup.core.domain.usecase.DayUseCases
import com.example.stepup.target.data.repository.TargetRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

class ProgressViewModel(
    private val dayUseCases: DayUseCases,
    private val currentDateFlow: StateFlow<LocalDate>
) : ViewModel() {

    private val _progress = MutableStateFlow(
        ProgressState(
            date = LocalDate.MIN,
            stepsTaken = 0,
            dailyGoal = 0,
            calorieBurned = 0,
            distanceTravelled = 0.0,
            carbonDioxideSaved = 0.0
        )
    )
    val progress: StateFlow<ProgressState> = _progress.asStateFlow()

    private var getProgressJob: Job? = null

    init {
        viewModelScope.launch {
            currentDateFlow.collect  {date ->
                getProgress(date)
            }
        }
    }

    private fun getProgress(date: LocalDate) {
        getProgressJob?.cancel()

        getProgressJob = dayUseCases.getDay(date).onEach { day ->
            _progress.value = progress.value.copy(
                date = day.date,
                stepsTaken = day.steps,
                dailyGoal = day.goal,
                calorieBurned = day.calorieBurned.roundToInt(),
                distanceTravelled = day.distanceTravelled,
                carbonDioxideSaved = day.carbonDioxideSaved
            )
        }.launchIn(viewModelScope)
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as StepUpApplication

            val targetRepository = TargetRepositoryImpl(application.targetStore)
            val dayRepository = DayRepositoryImpl(application.stepUpDatabase.dayDao)
            val dayUseCases = DayUseCases(dayRepository, targetRepository)
            val currentDateFlow = application.currentDate

            return ProgressViewModel(dayUseCases, currentDateFlow) as T
        }
    }
}