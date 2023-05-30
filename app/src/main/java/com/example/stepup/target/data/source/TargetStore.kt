package com.example.stepup.target.data.source

import com.example.stepup.target.domain.model.Target
import kotlinx.coroutines.flow.Flow

interface TargetStore {
    fun getTarget(): Flow<Target>
}
