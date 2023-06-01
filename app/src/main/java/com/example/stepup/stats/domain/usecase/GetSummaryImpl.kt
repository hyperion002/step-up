package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.model.StatsSummary
import com.example.stepup.core.domain.repository.DayRepository

class GetSummaryImpl(
    private val dayRepository: DayRepository
) : GetSummary {
    override suspend fun invoke(): StatsSummary {
        val allDays = dayRepository.getAllDays()
        return StatsSummary.of(allDays)
    }
}