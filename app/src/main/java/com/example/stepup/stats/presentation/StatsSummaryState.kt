package com.example.stepup.stats.presentation

data class StatsSummaryState(
    val isRefreshing: Boolean = false,
    val stepsTaken: Long = 0L,
    val calorieBurned: Double = 0.0,
    val distanceTravelled: Double = 0.0,
    val carbonDioxideSaved: Double = 0.0,
)
