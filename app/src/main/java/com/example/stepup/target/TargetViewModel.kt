package com.example.stepup.target

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.stepup.StepUpApplication
import com.example.stepup.core.data.repository.DayRepositoryImpl
import com.example.stepup.core.domain.model.DayTarget
import com.example.stepup.target.data.repository.TargetRepositoryImpl
import com.example.stepup.target.domain.usecase.TargetUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate

class TargetViewModel(
    private val targetUseCases: TargetUseCases
) : ViewModel() {

    private var observeTargetChangesJob: Job? = null

    fun observeTargetChanges() {
        observeTargetChangesJob?.cancel()
        observeTargetChangesJob = targetUseCases.getTarget().onEach {
            targetUseCases.updateDayTarget(
                DayTarget(
                    date = LocalDate.now(),
                    goal = it.dailyGoal,
                    height = it.height,
                    weight = it.weight,
                    stepLength = it.stepLength,
                    pace = it.pace
                )
            )
        }.launchIn(viewModelScope)
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val application = checkNotNull(extras[APPLICATION_KEY]) as StepUpApplication

            val targetRepository = TargetRepositoryImpl(application.targetStore)
            val dayRepository = DayRepositoryImpl(application.stepUpDatabase.dayDao)

            val targetUseCases = TargetUseCases(targetRepository, dayRepository)

            return TargetViewModel(targetUseCases) as T
        }
    }
}