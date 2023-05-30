package com.example.stepup.target.data.repository

import com.example.stepup.target.data.source.TargetStore
import com.example.stepup.target.domain.model.Target
import com.example.stepup.target.domain.repository.TargetRepository
import kotlinx.coroutines.flow.Flow

class TargetRepositoryImpl(private val targetStore: TargetStore) : TargetRepository {
    override fun getTarget(): Flow<Target> {
        return targetStore.getTarget()
    }
}