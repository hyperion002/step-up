package com.example.stepup.target.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository
import com.example.stepup.target.domain.repository.TargetRepository

class TargetUseCases(
    targetRepository: TargetRepository,
    dayRepository: DayRepository
) {
    val getTarget: GetTarget = GetTargetImpl(targetRepository)
    val updateDayTarget: UpdateDayTarget = UpdateDayTargetImpl(dayRepository)
}