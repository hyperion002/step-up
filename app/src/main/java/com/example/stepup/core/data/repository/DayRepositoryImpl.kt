package com.example.stepup.core.data.repository

import com.example.stepup.core.data.source.DayDao
import com.example.stepup.core.domain.model.Day
import com.example.stepup.core.domain.model.DayTarget
import com.example.stepup.core.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DayRepositoryImpl(private val dao: DayDao) : DayRepository {
    override fun getFirstDay(): Flow<Day?> {
        return dao.getFirstDay()
    }

    override fun getDay(date: LocalDate): Flow<Day?> {
        return dao.getDay(date)
    }

    override suspend fun getAllDays(): List<Day> {
        return dao.getAllDays()
    }

    override fun getDays(start: LocalDate, endInclusive: LocalDate): Flow<List<Day>> {
        return dao.getDays(start, endInclusive)
    }

    override suspend fun upsertDay(day: Day) {
        dao.upsertDay(day)
    }

    override suspend fun updateDayTarget(dayTarget: DayTarget) {
        dao.updateDayTarget(dayTarget)
    }

}