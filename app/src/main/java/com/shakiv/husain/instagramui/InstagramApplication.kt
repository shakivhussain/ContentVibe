package com.shakiv.husain.instagramui

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class InstagramApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        FirebaseApp.initializeApp(this);
        Timber.plant(Timber.DebugTree())
    }

}