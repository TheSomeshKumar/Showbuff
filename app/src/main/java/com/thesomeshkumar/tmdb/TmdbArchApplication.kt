package com.thesomeshkumar.tmdb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.color.DynamicColors
import com.google.android.material.elevation.SurfaceColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TmdbArchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
