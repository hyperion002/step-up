package com.example.stepup.stats.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.stepup.StepUpApplication
import com.example.stepup.core.data.repository.DayRepositoryImpl
import com.example.stepup.core.domain.usecase.DayUseCases
import com.example.stepup.stats.domain.usecase.StatsDetailsUseCases
import com.example.stepup.target.data.repository.TargetRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

class StatsDetailsViewModel(
    private val dayUseCases: DayUseCases,
    statsDetailsUseCases: StatsDetailsUseCases,
    currentDateFlow: StateFlow<LocalDate>
) : ViewModel() {

    private val _day = MutableStateFlow(
        StatsDetailsState(
            date = LocalDate.MIN,
            stepsTaken = 0,
            calorieBurned = 0,
            distanceTravelled = 0.0,
            carbonDioxideSaved = 0.0,
            chartDateRange = currentDateFlow.value..currentDateFlow.value
        )
    )
    val day: StateFlow<StatsDetailsState> = _day.asStateFlow()

    init {
        selectDay(currentDateFlow.value)

        viewModelScope.launch {
            val firstDateFlow = statsDetailsUseCases.getFirstDate()
            firstDateFlow
                .combine(currentDateFlow) { firstDate, currentDate ->
                    firstDate..currentDate
                }.collect { dataRange ->
                    _day.value = day.value.copy(chartDateRange = dataRange)
                }
        }
    }

    private var selectDateJob: Job? = null
    fun selectDay(date: LocalDate) {
        selectDateJob?.cancel()
        selectDateJob = dayUseCases.getDay(date).onEach {
            _day.value = day.value.copy(
                date = it.date,
                stepsTaken = it.steps,
                calorieBurned = it.calorieBurned.roundToInt(),
                distanceTravelled = it.distanceTravelled,
                carbonDioxideSaved = it.carbonDioxideSaved
            )
        }.launchIn(viewModelScope)
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as StepUpApplication

            val dayRepository = DayRepositoryImpl(application.stepUpDatabase.dayDao)
            val targetRepository = TargetRepositoryImpl(application.targetStore)
            val dayUseCases = DayUseCases(dayRepository, targetRepository)
            val statsDetailsUseCases = StatsDetailsUseCases(dayRepository)

            return StatsDetailsViewModel(
                dayUseCases,
                statsDetailsUseCases,
                application.currentDate
            ) as T
        }
    }
}