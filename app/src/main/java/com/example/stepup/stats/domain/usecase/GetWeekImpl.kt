package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.model.Day
import com.example.stepup.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetWeekImpl(
    private val dayRepository: DayRepository
) : GetWeek {
    override fun invoke(startingAt: LocalDate): Flow<List<Day>> {
        val endingAt = startingAt.plusDays(6)
        return dayRepository.getDays(startingAt..endingAt)
    }
}