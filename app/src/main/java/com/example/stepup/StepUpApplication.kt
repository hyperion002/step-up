package com.example.stepup

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.example.stepup.core.data.source.StepUpDatabase
import com.example.stepup.target.data.source.TargetStore
import com.example.stepup.target.data.source.TargetStoreImpl
import com.google.android.material.color.DynamicColors
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

class StepUpApplication: Application() {

    lateinit var targetStore: TargetStore
    lateinit var stepUpDatabase: StepUpDatabase

    val currentDate = MutableStateFlow<LocalDate>(LocalDate.now())

    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)
        PreferenceManager.setDefaultValues(this, R.xml.target, false)
        registerMidnightTimer()

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        targetStore = TargetStoreImpl(sharedPreferences)

        stepUpDatabase = Room.databaseBuilder(
            applicationContext,
            StepUpDatabase::class.java,
            StepUpDatabase.DATABASE_NAME
        ).build()
    }

    private fun registerMidnightTimer() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
        }
        registerReceiver(midnightBroadcastReceiver, intentFilter)
    }

    private val midnightBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val today = LocalDate.now()
            if (today != currentDate.value) {
                currentDate.value = today
            }
        }
    }
}