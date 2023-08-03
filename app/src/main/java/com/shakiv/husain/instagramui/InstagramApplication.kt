package com.shakiv.husain.instagramui

import android.app.Application
import com.shakiv.husain.instagramui.data.AppContainer
import com.shakiv.husain.instagramui.data.AppContainerImp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InstagramApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImp(this)

    }

}