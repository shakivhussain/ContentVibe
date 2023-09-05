package com.shakiv.husain.instagramui

import android.app.Application
import com.google.firebase.FirebaseApp
import com.shakiv.husain.instagramui.data.AppContainer
import com.shakiv.husain.instagramui.data.AppContainerImp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InstagramApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this);
    }

}