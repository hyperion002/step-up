package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetFirstDateImpl(
    private val dayRepository: DayRepository
) : GetFirstDate {
    override fun invoke(): Flow<LocalDate> {
        return dayRepository.getFirstDay().map {
            it?.date ?: LocalDate.now()
        }
    }
}