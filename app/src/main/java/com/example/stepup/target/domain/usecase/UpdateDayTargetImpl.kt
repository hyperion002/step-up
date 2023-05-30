package com.example.stepup.target.domain.usecase

import com.example.stepup.core.domain.model.DayTarget
import com.example.stepup.core.domain.repository.DayRepository

class UpdateDayTargetImpl(
    private val dayRepository: DayRepository
) : UpdateDayTarget {

    override suspend fun invoke(dayTarget: DayTarget) {
        dayRepository.updateDayTarget(dayTarget)
    }
}
