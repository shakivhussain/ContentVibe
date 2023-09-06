package com.shakiv.husain.instagramui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.shakiv.husain.instagramui.presentation.app.InstagramApp
import com.shakiv.husain.instagramui.presentation.theme.InstagramUITheme
import com.shakiv.husain.instagramui.utils.extentions.logd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InstagramUITheme {
                InstagramApp()
            }
        }
    }
}
