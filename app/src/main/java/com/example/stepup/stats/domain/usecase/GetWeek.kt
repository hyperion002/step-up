package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GetWeek {
    operator fun invoke(startingAt: LocalDate): Flow<List<Day>>
}