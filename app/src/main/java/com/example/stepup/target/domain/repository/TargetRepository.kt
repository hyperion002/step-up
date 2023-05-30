package com.example.stepup.target.domain.repository

import com.example.stepup.target.domain.model.Target
import kotlinx.coroutines.flow.Flow

interface TargetRepository {
    fun getTarget(): Flow<Target>
}