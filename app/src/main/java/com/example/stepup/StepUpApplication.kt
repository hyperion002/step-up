package com.example.stepup

import android.app.Application
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.stepup.core.data.source.StepUpDatabase
import com.example.stepup.target.data.source.TargetStore
import com.example.stepup.target.data.source.TargetStoreImpl
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

class StepUpApplication: Application() {

    lateinit var targetStore: TargetStore
    lateinit var stepUpDatabase: StepUpDatabase

    val currentDate = MutableStateFlow<LocalDate>(LocalDate.now())

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        targetStore = TargetStoreImpl(sharedPreferences)

        stepUpDatabase = Room.databaseBuilder(
            applicationContext,
            StepUpDatabase::class.java,
            StepUpDatabase.DATABASE_NAME
        ).build()
    }
}