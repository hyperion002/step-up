package com.example.stepup.core.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "day")
data class Day(
    @PrimaryKey
    val date: LocalDate,
    val steps: Int = 0,
    val goal: Int,
    val height: Int = 170,
    val weight: Int = 54,
    @ColumnInfo(name = "step_length") val stepLength: Int = 72,
    val pace: Double = 1.0
) {
    companion object

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

// TODO: Move to companion object scope inside the class
