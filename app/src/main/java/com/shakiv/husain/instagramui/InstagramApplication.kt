package com.shakiv.husain.instagramui

import android.app.Application
import com.shakiv.husain.instagramui.data.AppContainer
import com.shakiv.husain.instagramui.data.AppContainerImp

class InstagramApplication : Application() {


    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImp(this)

    }


}