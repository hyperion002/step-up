package com.example.stepup.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stepup.target.domain.model.Target
import java.time.LocalDate

@Entity(tableName = "day")
data class Day(
    @PrimaryKey
    val date: LocalDate,
    val steps: Int = 0,
    val goal: Int,
    val height: Int = 170,
    val weight: Int = 54,
    val stepLength: Int = 72,
    val pace: Double = 1.0
) {
    companion object {
        fun of(date: LocalDate, target: Target, steps: Int = 0): Day {
            return target.run {
                Day(
                    date = date,
                    steps = steps,
                    goal = dailyGoal,
                    height = height,
                    weight = weight,
                    stepLength = stepLength,
                    pace = pace
                )
            }
        }
    }

    val distanceTravelled
        get() = run {
            val distanceCentimeters = steps * stepLength
            distanceCentimeters.toDouble() / 100_100
        }

    val calorieBurned
        get() = run {
            val modifier = height / 182.0 + weight / 70.0 - 1
            0.04 * steps * pace * modifier
        }

    val carbonDioxideSaved
        get() = run {
            steps * 0.1925 / 1000.0
        }
}