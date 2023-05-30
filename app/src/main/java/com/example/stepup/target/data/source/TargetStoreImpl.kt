package com.example.stepup.target.data.source

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import com.example.stepup.target.domain.model.Target
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TargetStoreImpl(
    private val sharedPreferences: SharedPreferences
) : TargetStore, OnSharedPreferenceChangeListener {

    private val target: MutableStateFlow<Target>

    init {
        val parsedTarget = parseTarget(sharedPreferences)
        target = MutableStateFlow(parsedTarget)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun parseTarget(sharedPreferences: SharedPreferences): Target =
        sharedPreferences.run {
            Target(
                dailyGoal = getNumericString("daily_goal", 0),
                stepLength = getNumericString("step_length", 0),
                height = getNumericString("height", 0),
                weight = getNumericString("weight", 0),
                pace = getNumericString("pace", 0.0)
            )
        }

    private fun SharedPreferences.getNumericString(key: String, defaultValue: Int): Int =
        getString(key, "")?.toIntOrNull() ?: defaultValue

    private fun SharedPreferences.getNumericString(key: String, defaultValue: Double): Double =
        getString(key, "")?.toDoubleOrNull() ?: defaultValue

    override fun getTarget(): Flow<Target> {
        return target.asStateFlow()
    }

    override fun onSharedPreferenceChanged(updatedSharedPreferences: SharedPreferences?, key: String?) {
        target.value = parseTarget(sharedPreferences)
    }
}