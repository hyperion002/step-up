package com.example.stepup.core.domain.repository

import com.example.stepup.core.domain.model.Day
import com.example.stepup.core.domain.model.DayTarget
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DayRepository {
    fun getFirstDay(): Flow<Day?>

    fun getDay(date: LocalDate): Flow<Day?>

    suspend fun getAllDays(): List<Day>

    fun getDays(range: ClosedRange<LocalDate>): Flow<List<Day>>

    suspend fun upsertDay(day: Day)

    suspend fun updateDayTarget(dayTarget: DayTarget)
}
