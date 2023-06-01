package com.example.stepup.stats.domain.usecase

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GetFirstDate {
    operator fun invoke(): Flow<LocalDate>
}