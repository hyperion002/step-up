package com.example.stepup.stats.domain.usecase

import com.example.stepup.core.domain.model.StatsSummary

interface GetSummary {
    suspend operator fun invoke(): StatsSummary
}