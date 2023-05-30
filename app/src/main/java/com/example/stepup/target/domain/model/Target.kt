package com.example.stepup.target.domain.model

data class Target(
    val dailyGoal: Int,
    val stepLength: Int,
    val height: Int,
    val weight: Int,
    val pace: Double
)
