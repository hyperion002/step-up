package com.example.stepup.core.domain.model

import java.time.LocalDate

data class DayTarget(
    val date: LocalDate,
    val goal: Int,
    val height: Int,
    val weight: Int,
    val stepLength: Int,
    val pace: Double
)
