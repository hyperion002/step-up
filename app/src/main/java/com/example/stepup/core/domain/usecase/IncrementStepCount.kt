package com.example.stepup.core.domain.usecase

import java.time.LocalDate

interface IncrementStepCount {
    suspend operator fun invoke(date: LocalDate, by: Int)
}
