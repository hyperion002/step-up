package com.example.stepup.service

import java.time.LocalDate

data class StepCounterEvent(
    val stepCount: Int,
    val eventDate: LocalDate
)