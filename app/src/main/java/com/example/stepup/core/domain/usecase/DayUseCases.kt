package com.example.stepup.core.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository
import com.example.stepup.target.domain.repository.TargetRepository

class DayUseCases(
    dayRepository: DayRepository,
    targetRepository: TargetRepository
) {
    val getDay: GetDay = GetDayImpl(dayRepository, targetRepository)
    val incrementStepCount: IncrementStepCount = IncrementStepCountImpl(dayRepository, getDay)
}