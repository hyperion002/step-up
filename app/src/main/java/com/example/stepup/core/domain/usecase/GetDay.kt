package com.example.stepup.core.domain.usecase

import com.example.stepup.core.domain.model.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GetDay {

    operator fun invoke(date: LocalDate): Flow<Day>
}
