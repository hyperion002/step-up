package com.example.stepup.service

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class StepCounterServiceLauncher : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.run {
            if (intent?.action == Intent.ACTION_BOOT_COMPLETED && hasPermission(context)) {
                val launchIntent = Intent(applicationContext, StepCounterService::class.java)
                ContextCompat.startForegroundService(applicationContext, launchIntent)
            }
        }
    }

    private fun hasPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }
}