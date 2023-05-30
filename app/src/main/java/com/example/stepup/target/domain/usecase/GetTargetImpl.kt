package com.example.stepup.target.domain.usecase

import com.example.stepup.target.domain.model.Target
import com.example.stepup.target.domain.repository.TargetRepository
import kotlinx.coroutines.flow.Flow

class GetTargetImpl(
    private val repository: TargetRepository
) : GetTarget {

    override fun invoke(): Flow<Target> {
        return repository.getTarget()
    }
}
