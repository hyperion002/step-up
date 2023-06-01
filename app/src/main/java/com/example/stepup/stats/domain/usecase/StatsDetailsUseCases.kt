package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository

class StatsDetailsUseCases(
    dayRepository: DayRepository
) {
    val getFirstDate: GetFirstDate = GetFirstDateImpl(dayRepository)
}