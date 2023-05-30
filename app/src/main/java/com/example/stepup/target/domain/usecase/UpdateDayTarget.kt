package com.example.stepup.target.domain.usecase

import com.example.stepup.core.domain.model.DayTarget

interface UpdateDayTarget {

    suspend operator fun invoke(dayTarget: DayTarget)
}
