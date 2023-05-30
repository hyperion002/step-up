package com.example.stepup.core.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class IncrementStepCountImpl(
    private val repository: DayRepository,
    private val getDayUseCase: GetDay
) : IncrementStepCount {

    override suspend fun invoke(date: LocalDate, by: Int) {
        val day = getDayUseCase(date).first()
        val updatedDay = day.copy(steps = day.steps + by)
        repository.upsertDay(updatedDay)
    }
}
