package com.thesomeshkumar.showbuff.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.elevation.SurfaceColors
import com.thesomeshkumar.showbuff.R
import com.thesomeshkumar.showbuff.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        val surfaceColors2 = SurfaceColors.SURFACE_2.getColor(this)
        val surfaceColors5 = SurfaceColors.SURFACE_0.getColor(this)
        window.apply {
            window.statusBarColor = surfaceColors5
            window.navigationBarColor = surfaceColors2
        }
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.tv_show_fragment, R.id.movies_fragment)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
