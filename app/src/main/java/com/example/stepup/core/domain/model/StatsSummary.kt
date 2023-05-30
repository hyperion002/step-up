package com.example.stepup.core.domain.model

data class StatsSummary(
    val stepsTaken: Long = 0L,
    val calorieBurned: Double = 0.0,
    val distanceTravelled: Double = 0.0,
    val carbonDioxideSaved: Double = 0.0
) {
    companion object {
        fun of(days: List<Day>): StatsSummary {
            val stepsTaken = days.sumOf { it.steps.toLong() }
            val calorieBurned = days.sumOf { it.calorieBurned }
            val distanceTravelled = days.sumOf { it.distanceTravelled }
            val carbonDioxideSaved = days.sumOf { it.carbonDioxideSaved }
            return StatsSummary(
                stepsTaken,
                calorieBurned,
                distanceTravelled,
                carbonDioxideSaved
            )
        }
    }
}