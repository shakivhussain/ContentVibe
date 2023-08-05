package com.shakiv.husain.instagramui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import com.shakiv.husain.instagramui.ui.InstagramApp
import com.shakiv.husain.instagramui.ui.theme.InstagramUITheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as InstagramApplication).container

        setContent {
            InstagramUITheme {

                Surface(tonalElevation = 5.dp) {
                    InstagramApp(appContainer)
                }
            }
        }
    }
}
