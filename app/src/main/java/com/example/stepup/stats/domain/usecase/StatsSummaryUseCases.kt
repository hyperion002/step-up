package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository

class StatsSummaryUseCases(
    dayRepository: DayRepository
) {
    val getSummary: GetSummary = GetSummaryImpl(dayRepository)
}