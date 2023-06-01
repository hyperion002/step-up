package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository

class StatsChartPageUseCases(
    dayRepository: DayRepository
) {
    val getWeek: GetWeek = GetWeekImpl(dayRepository)
}