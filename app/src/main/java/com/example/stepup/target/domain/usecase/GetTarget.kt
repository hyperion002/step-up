package com.example.stepup.target.domain.usecase

import com.example.stepup.target.domain.model.Target
import kotlinx.coroutines.flow.Flow

interface GetTarget {

    operator fun invoke(): Flow<Target>
}
