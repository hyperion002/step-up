package com.example.stepup.core.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stepup.R
import com.example.stepup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var requestMultiplePermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Permissions required by the app
    private val permissionList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)

        // Function to request permissions
        requestPermissions()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.progressFragment,
                R.id.statsFragment,
                R.id.targetFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun requestPermissions() {
        fun hasPermission(permission: String): Boolean =
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

        requestMultiplePermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !hasPermission(Manifest.permission.ACTIVITY_RECOGNITION)) {
            permissionList.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasPermission(Manifest.permission.POST_NOTIFICATIONS)) {
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        requestMultiplePermissionLauncher.launch(permissionList.toTypedArray())
    }
}