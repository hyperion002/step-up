package com.example.stepup.core.domain.usecase

import com.example.stepup.core.domain.model.Day
import com.example.stepup.core.domain.repository.DayRepository
import com.example.stepup.target.domain.repository.TargetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class GetDayImpl(
    private val dayRepository: DayRepository,
    private val targetRepository: TargetRepository
) : GetDay {

    override fun invoke(date: LocalDate): Flow<Day> {
        val targetFlow = targetRepository.getTarget()
        val dayFlow = dayRepository.getDay(date)

        return targetFlow.combine(dayFlow) { target, day ->
            day ?: Day.of(date, target, steps = 0)
        }
    }
}
